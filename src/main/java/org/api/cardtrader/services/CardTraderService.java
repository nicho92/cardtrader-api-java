package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.api.cardtrader.enums.ConditionEnum;
import org.api.cardtrader.enums.Identifier;
import org.api.cardtrader.enums.VersionEnum;
import org.api.cardtrader.modele.App;
import org.api.cardtrader.modele.BluePrint;
import org.api.cardtrader.modele.Categorie;
import org.api.cardtrader.modele.Expansion;
import org.api.cardtrader.modele.Game;
import org.api.cardtrader.modele.MarketProduct;
import org.api.cardtrader.modele.Order;
import org.api.cardtrader.tools.CacheManager;
import org.api.cardtrader.tools.JsonTools;
import org.api.cardtrader.tools.URLUtilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CardTraderService {

	private static final String MARKETPLACE_PRODUCTS = "marketplace/products";
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
	
	
	public CardTraderService(String token) {
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
	
	public static void main(String[] args) {
		String k = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJjYXJkdHJhZGVyLXByb2R1Y3Rpb24iLCJzdWIiOiJhcHA6OCIsImF1ZCI6ImFwcDo4IiwiZXhwIjo0Nzg5NzE3MDAwLCJqdGkiOiJlODM1YmNlNy0xOTI3LTRlOGItOTA4NC1mMzk2NjI1OGFhZTMiLCJpYXQiOjE2MzQwNDM0MDAsIm5hbWUiOiJNYW5hZ2VtZW50U29mdHdhcmUifQ.VnbfRgDzcSa5InyeVDIaxEjKrCyiP5On1B6lb8hkhFhqMoKkREUeKa1SGKLNJF6JbLGKZ4BhH2r-ucaLJfCSu7onVDNB7s1b2gv7D92Xzyhtp_grOABHGSyTSUoFH5S7Uao0pr_4GhD1JH2Y_PB9dU_lhvKixJ6OeefpcEW7n2-1yxS_cDD0WF4S_DaBsBOuU7mBGRBJYpRfhTepmIuadEimMuI8J9bwmhII93QbccUJiHamxH1miigw4k6Edz1fufr822tqaHnPZ5sHDD04lEYu_l3osPXPDka86bgyhfC6uJhF3h0VnFn791VJlGXGciXF-zs1eIKY86lv25elCA";
		
		new CardTraderService(k).listGames();
	}
	
	public List<MarketProduct> listMarketProduct(Expansion exp){
		return listMarketProduct(exp.getId()); 
	}
	
	
	public List<MarketProduct> listMarketProduct(Integer expansionid)
	{
		var ret = new ArrayList<MarketProduct>();
		
		var arr= caches.getCached(MARKETPLACE_PRODUCTS+expansionid, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+MARKETPLACE_PRODUCTS+"?expansion_id="+expansionid).getAsJsonArray();
			}
		}).getAsJsonArray();
		
	
		arr.forEach(je->{
			
		});
		
		
		return ret;
	}
	
	public List<BluePrint> listBluePrints(Categorie category, String name, Expansion expansion)
	{
		return listBluePrintsByIds(category==null?null:category.getId(),name, expansion==null?null:expansion.getId());
	}
	
	public List<BluePrint> listBluePrintsByIds(Integer categoryId, String name, Integer expansionid)
	{
		
		var arr= caches.getCached(BLUEPRINTS+name, new Callable<JsonElement>() {
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
	     	 
	     	  if(!obj.get("version").isJsonNull() && !obj.get("version").getAsString().isBlank())
	     		  b.setVersion(VersionEnum.valueOf(obj.get("version").getAsString().toUpperCase()));
	     	  
	     	  
	     	  b.setSlug(obj.get("slug").getAsString());
	     	  b.setMkmId(obj.get("mkm_id").getAsInt());
	     	  b.setScryfallId(obj.get("scryfall_id").getAsString());
	 		  b.setGame(listGames().stream().filter(g->g.getId()==obj.get("game_id").getAsInt()).findFirst().orElse(null));
			  b.setCategorie(listCategories().stream().filter(g->g.getId()==obj.get("category_id").getAsInt()).findFirst().orElse(null));
			  b.setExpansion(listExpansions().stream().filter(g->g.getId()==obj.get("expansion_id").getAsInt()).findFirst().orElse(null));
			  b.setProductUrl(CardTraderConstants.CARDTRADER_WEBSITE_URI+"cards/"+b.getSlug()+"?share_code="+CardTraderConstants.SHARE_CODE);
		
			  if(obj.get("fixed_properties").getAsJsonObject().get("collector_number")!=null)
				  b.setCollectorNumber(obj.get("fixed_properties").getAsJsonObject().get("collector_number").getAsString());
			  
			  if(obj.get("image_url")!=null && !obj.get("image_url").getAsString().isBlank())
			  {
				  b.setImageUrl(CardTraderConstants.CARDTRADER_WEBSITE_URI+obj.get("image_url").getAsString());
			  }
			  else
			  {
				  b.setImageUrl("https://api.scryfall.com/cards/"+b.getScryfallId()+"?format=image");
			  }
	    		
			  
			  ret.add(b);
		});
		
		
		return ret;
	}
	
	public void downloadProducts(@Nonnull Integer gameId, @Nonnull Integer categoryId,File f) throws IOException
	{
		String url=CardTraderConstants.CARDTRADER_API_URI+"/products/export";
	
		network.download(url+"?game_id="+gameId+"&category_id="+categoryId, f);
	}
	
	public Integer addProduct(@Nonnull String identifier, @Nonnull Identifier idRef, @Nonnull double price, @Nonnull int qty, String description,ConditionEnum condition,String userDataField) throws IOException
	{
		
		var obj = new JsonObject();
		
		obj.addProperty(idRef.name(), identifier);
		obj.addProperty("price", price);
		obj.addProperty("quantity", qty);
		
		if(description!=null)
			obj.addProperty("description", description);
		
		if(userDataField!=null)
			obj.addProperty("user_data_field", userDataField);
		
		if(condition!=null)
		{
			var prop = new JsonObject();
					   prop.addProperty("condition", condition.getValue());
			
					   obj.add("properties", prop);
		}
		return network.doPost(CardTraderConstants.CARDTRADER_API_URI+"/products",obj);
		
	}
	
	public void updateProduct(@Nonnull Integer identifier, Double price, Integer qty, String description,ConditionEnum condition,String userDataField) throws IOException
	{
		
		var obj = new JsonObject();
		
		
		if(price!=null)
			obj.addProperty("price", price);
		
		
		if(qty!=null)
			obj.addProperty("quantity", qty);
		
		if(description!=null)
			obj.addProperty("description", description);
		
		if(userDataField!=null)
			obj.addProperty("user_data_field", userDataField);
		
		if(condition!=null)
		{
			var prop = new JsonObject();
					   prop.addProperty("condition", condition.getValue());
			
					   obj.add("properties", prop);
		}
		network.doPut(CardTraderConstants.CARDTRADER_API_URI+"/products/"+identifier,obj);
	}
	
	public void deleteProduct(@Nonnull Integer identifier) throws IOException
	{
		network.doDelete(CardTraderConstants.CARDTRADER_API_URI+"/products/"+identifier);
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
	
	public List<Order> listOrdersDetails(int idOrder)
	{
		return json.fromJsonList(caches.getCached(ORDERS+idOrder, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+ORDERS+"/"+idOrder).getAsJsonArray();
			}
		}),Order.class);
	}
	
}
