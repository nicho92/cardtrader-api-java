package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.api.cardtrader.modele.App;
import org.api.cardtrader.modele.Categorie;
import org.api.cardtrader.modele.Expansion;
import org.api.cardtrader.modele.Game;
import org.api.cardtrader.tools.CacheItem;
import org.api.cardtrader.tools.JsonTools;
import org.api.cardtrader.tools.URLUtilities;

public class ApplicationService {

	JsonTools json;
	URLUtilities network; 
	private String token;
	protected Logger logger = Logger.getLogger(this.getClass());
	
	
	
	private CacheItem<Game> gamesCache;
	private CacheItem<Expansion> expansionCache;
	
	
	public static void main(String[] args) throws IOException {
		var serv = new ApplicationService(Files.readString(new File("D:\\Desktop\\key").toPath()));
		
		
		serv.listExpansions().forEach(e->{
			
			System.out.println(e.getGame() + " " + e.getName()+ " " + e.getCode());
			
		});
	}
	
	
	public ApplicationService(String token) {
		json = new JsonTools();
		network = new URLUtilities();
		network.initToken(token);
		this.token=token;
	}
	

	public String getToken() {
		return token;
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
		try {
			return json.fromJsonList(network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/games").getAsJsonArray(), Game.class);
		} catch (IOException e) {
			logger.error(e);
			return new ArrayList<>();
		}
	}
	
	public List<Categorie> listCategories()
	{
		try {
			return json.fromJsonList(network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/categories?game_id=1").getAsJsonArray(),Categorie.class);
		} catch (IOException e) {
			logger.error(e);
			return new ArrayList<>();
		}
	
	}
	
	public List<Expansion> listExpansions()
	{
		try {
			List<Expansion> list= json.fromJsonList(network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/expansions?game_id=1").getAsJsonArray(),Expansion.class);
			
			List<Game> games = listGames();
			
			list.forEach(ex->ex.setGame(games.stream().filter(g->g.getId()==ex.getGameId()).findFirst().orElse(null)));
			
			return list;
			
			
		} catch (IOException e) {
			logger.error(e);
			return new ArrayList<>();
		}
	
	}
	
}
