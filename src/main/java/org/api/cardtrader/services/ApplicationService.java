package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.api.cardtrader.modele.App;
import org.api.cardtrader.modele.Categorie;
import org.api.cardtrader.modele.Expansion;
import org.api.cardtrader.modele.Game;
import org.api.cardtrader.tools.CacheManager;
import org.api.cardtrader.tools.JsonTools;
import org.api.cardtrader.tools.URLUtilities;

import com.google.gson.JsonElement;

public class ApplicationService {

	private static final String EXPANSIONS = "expansions";
	private static final String CATEGORIES = "categories";
	private static final String GAMES = "games";
	private JsonTools json;
	private URLUtilities network; 
	private String token;
	protected Logger logger = Logger.getLogger(this.getClass());
	
	private CacheManager<JsonElement> caches;
	
	
	public static void main(String[] args) throws IOException {
		var serv = new ApplicationService(Files.readString(new File("D:\\Desktop\\key").toPath()));

		serv.listExpansions().forEach(ex->{
			System.out.println(ex);
		});
		
	}
	
	
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
	
}
