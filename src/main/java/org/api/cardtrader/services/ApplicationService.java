package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;
import org.api.cardtrader.enums.VersionEnum;
import org.api.cardtrader.modele.App;
import org.api.cardtrader.modele.BluePrint;
import org.api.cardtrader.modele.Categorie;
import org.api.cardtrader.modele.Expansion;
import org.api.cardtrader.modele.Game;
import org.api.cardtrader.modele.Order;
import org.api.cardtrader.tools.CacheManager;
import org.api.cardtrader.tools.JsonTools;
import org.api.cardtrader.tools.URLUtilities;

import com.google.gson.JsonElement;

public class ApplicationService {

	private static final String ORDERS = "orders";
	private static final String BLUEPRINTS = "blueprints";
	private static final String EXPANSIONS = "expansions";
	private static final String CATEGORIES = "categories";
	private static final String GAMES = "games";
	private JsonTools json;
	private URLUtilities network; 
	private String token;
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private CacheManager<JsonElement> caches;
	
	
	public ApplicationService(String token) {
		json = new JsonTools();
		network = new URLUtilities();
		network.initToken(token);
		this.token=token;
		caches = new CacheManager<>();
	}
	

	public String getToken() {
		return token;
	}
	
	public void clearCache(String k)
	{
		caches.clear(k);
	}
	
	
	public App getApp()
	{
		try {
			return json.fromJson(network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/info"), App.class);
		} catch (IOException e) {
			logger.error(e);
			return null;
		}
	}
	
	
	public List<Game> listGames()
	{
			return json.fromJsonList(caches.getCached(GAMES, new Callable<JsonElement>() {
				@Override
				public JsonElement call() throws Exception {
					return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+GAMES).getAsJsonArray();
				}
			}),Game.class);
	}
	
	public List<Categorie> listCategories()
	{
		List<Categorie> ret= json.fromJsonList(caches.getCached(CATEGORIES, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+CATEGORIES).getAsJsonArray();
			}
		}),Categorie.class);
		
		
		ret.forEach(ex->ex.setGame(listGames().stream().filter(g->g.getId()==ex.getGameId()).findFirst().orElse(null)));

		
		return ret;
	}
	
	public List<Expansion> listExpansions()
	{
		List<Expansion> ret = json.fromJsonList(caches.getCached(EXPANSIONS, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+EXPANSIONS).getAsJsonArray();
			}
		}),Expansion.class);
		ret.forEach(ex->ex.setGame(listGames().stream().filter(g->g.getId()==ex.getGameId()).findFirst().orElse(null)));
		return ret;
	}
	
	public List<BluePrint> listBluePrints(Categorie category, String name, Expansion expansion)
	{
		return listBluePrints(category.getId(),name, expansion.getId());
	}
	
	
	public List<BluePrint> listBluePrints(Integer categoryId, String name, Integer expansionid)
	{
		
		var arr= caches.getCached(BLUEPRINTS, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				
				String extra=null;
				
				if(categoryId!=null)
				{
					extra="category_id="+categoryId;
				}
				
				if(name!=null)
				{
					if(extra==null)
							extra="name="+name.replace(" ", "%20");
						else 
							extra+="&name="+name.replace(" ", "%20");
				}
				
				if(expansionid!=null)
				{
					if(extra==null)
						extra="expansion_id="+expansionid;
					else 
						extra+="&expansion_id="+expansionid;
				}
				
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+BLUEPRINTS+""+(extra!=null?"?"+extra:"")).getAsJsonArray();
			}
		}).getAsJsonArray();
	
		List<BluePrint> ret = new ArrayList<>();
		arr.forEach(a->{
			  var obj = a.getAsJsonObject();
			  var b = new BluePrint();
	     	  b.setId(obj.get("id").getAsInt());
	     	  b.setName(obj.get("name").getAsString());
	     	  
	     	  if(!obj.get("version").isJsonNull())
	     		  b.setVersion(VersionEnum.valueOf(obj.get("version").getAsString().toUpperCase()));
	     	 
	     	  b.setSlug(obj.get("slug").getAsString());
	     	  b.setMkmId(obj.get("mkm_id").getAsInt());
	     	  b.setScryfallId(obj.get("scryfall_id").getAsString());
	     	  b.setImageUrl(CardTraderConstants.CARDTRADER_WEBSITE_URI+obj.get("image_url").getAsString());
			  b.setGame(listGames().stream().filter(g->g.getId()==obj.get("game_id").getAsInt()).findFirst().orElse(null));
			  b.setCategorie(listCategories().stream().filter(g->g.getId()==obj.get("category_id").getAsInt()).findFirst().orElse(null));
			  b.setExpansion(listExpansions().stream().filter(g->g.getId()==obj.get("expansion_id").getAsInt()).findFirst().orElse(null));
			  b.setCollectorNumber(obj.get("fixed_properties").getAsJsonObject().get("collector_number").getAsString());
			  ret.add(b);
		});
		
		
		return ret;
	}
	
	//TODO doesn't work
	public void downloadProducts(@Nonnull Integer gameId, @Nonnull Integer categoryId,File f) throws IOException
	{
		String url=CardTraderConstants.CARDTRADER_API_URI+"/products/download";
	
		network.download(url+"?game_id="+gameId+"&category_id="+categoryId, f);
	}
	
	public List<Order> listOrders()
	{
		return json.fromJsonList(caches.getCached(ORDERS, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+ORDERS).getAsJsonArray();
			}
		}),Order.class);
	}
	
	
	
	
	

	public static void main(String[] args) throws IOException {
		var serv = new ApplicationService(Files.readString(new File("D:\\Desktop\\key").toPath()));
		
		serv.listOrders().forEach(o->{
			
			System.out.println(o);
		});
		
	}
	
	
	
	
	
}
