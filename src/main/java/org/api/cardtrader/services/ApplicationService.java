package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;

import org.api.cardtrader.modele.App;
import org.api.cardtrader.tools.CardTraderConstants;
import org.api.cardtrader.tools.URLUtilities;

import com.google.gson.Gson;

public class ApplicationService {

	
	public static void main(String[] args) throws IOException {
		
		ApplicationConfig.getInstance().setToken(new File("D:\\Desktop\\key"));
		var network = new URLUtilities();
		var info = network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/info");
		
		System.out.println(new Gson().fromJson(info, App.class));
		
		var games =  network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/games").getAsJsonArray();
		System.out.println(games);
		
		var categories = network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/categories?game_id=1").getAsJsonArray();
		System.out.println(categories);
		
		var expensions = network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/expansions").getAsJsonArray();
		System.out.println(expensions);
	}
	
	
}
