package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;
import org.api.cardtrader.enums.ConditionEnum;
import org.api.cardtrader.enums.Identifier;
import org.api.cardtrader.enums.VersionEnum;
import org.api.cardtrader.modele.Address;
import org.api.cardtrader.modele.App;
import org.api.cardtrader.modele.BluePrint;
import org.api.cardtrader.modele.Categorie;
import org.api.cardtrader.modele.Expansion;
import org.api.cardtrader.modele.Game;
import org.api.cardtrader.modele.MarketProduct;
import org.api.cardtrader.modele.Order;
import org.api.cardtrader.modele.Price;
import org.api.cardtrader.modele.User;
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
	private App app;
	
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
		
		if(app==null)
			try {
				app = json.fromJson(network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/info"), App.class);
			} catch (IOException e) {
				logger.error(e);
				return null;
			}
		
		
		return app;
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
	
	public Categorie getCategoryById(int id)
	{
		return listCategories().stream().filter(c->c.getId()==id).findFirst().orElse(null);
	}

	public Game getGameById(int id) {
		return listGames().stream().filter(c->c.getId()==id).findFirst().orElse(null);
	}

	public Expansion getExpansionById(int id) {
		return listExpansions().stream().filter(c->c.getId()==id).findFirst().orElse(null);
	}
	
	public Expansion getExpansionByCode(String code) {
		return listExpansions().stream().filter(c->c.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
	}
	
	
	public List<MarketProduct> listMarketProduct(Expansion exp){
		return listMarketProduct(exp.getId()); 
	}
	
	
	public List<MarketProduct> listStock(String search){
		
		var ret = new ArrayList<MarketProduct>();
		
		try {
			network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/products/export").getAsJsonArray().forEach(je->{
				
				if(je.toString().toLowerCase().contains(search.toLowerCase()))
				{
					ret.add(parseMarket(je.getAsJsonObject()));
				}
			});
		} catch (IOException e) {
			logger.error("error getting stock",e );
		}
		
		return ret;
	}
	
	
	public List<MarketProduct> listStock(){
		
		var ret = new ArrayList<MarketProduct>();
		
		try {
			network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/products/export").getAsJsonArray().forEach(je->{
				var obj = je.getAsJsonObject();
				ret.add(parseMarket(obj));
			});
		} catch (IOException e) {
			logger.error("error getting stock",e );
		}
		
		return ret;
	}
	
	

	private MarketProduct parseMarket(JsonObject obj) {
		var mk  = new MarketProduct();
		
		if(!obj.get("description").isJsonNull())
		 mk.setDescription(obj.get("description").getAsString());
		
		if(obj.get("category_id")!=null)
			mk.setCategorie(getCategoryById(obj.get("category_id").getAsInt()));
		
		if(obj.get("game_id")!=null)
			mk.setGame(getGameById(obj.get("game_id").getAsInt()));
		
		if(obj.get("expansion")!=null)
			mk.setExpansion(getExpansionByCode(obj.get("expansion").getAsJsonObject().get("code").getAsString()));
		
		mk.setQty(obj.get("quantity").getAsInt());
		mk.setPrice(new Price(Double.valueOf((obj.get("price_cents").getAsInt()/100.0)),obj.get("price_currency").getAsString()));
		mk.setBundledQuantity(obj.get("bundled_quantity").getAsInt());
		mk.setIdBlueprint(obj.get("blueprint_id").getAsInt());
		mk.setBundle(obj.get("bundle").getAsBoolean());
		mk.setId(obj.get("id").getAsInt());			
		mk.setNameEn(obj.get("name_en").getAsString());
		mk.setGraded(obj.get("graded").getAsBoolean());
		
		
		  if(obj.get("properties_hash").getAsJsonObject().get("mtg_foil")!=null)
			  	mk.setFoil(obj.get("properties_hash").getAsJsonObject().get("mtg_foil").getAsBoolean());
		  
		  if(obj.get("properties_hash").getAsJsonObject().get("signed")!=null)
			  	mk.setSigned(obj.get("properties_hash").getAsJsonObject().get("signed").getAsBoolean());
		  
		  if(obj.get("properties_hash").getAsJsonObject().get("altered")!=null)
			  	mk.setAltered(obj.get("properties_hash").getAsJsonObject().get("altered").getAsBoolean());
			  
		  if(obj.get("properties_hash").getAsJsonObject().get("mtg_language")!=null)
			  	mk.setLanguage(obj.get("properties_hash").getAsJsonObject().get("mtg_language").getAsString());
		 
		  if(obj.get("properties_hash").getAsJsonObject().get("condition")!=null)
			  mk.setCondition(ConditionEnum.parseByLabel(obj.get("properties_hash").getAsJsonObject().get("condition").getAsString()));
		  
		  
		  if(obj.get("user")!=null)
		  {
			  var user = new User();
			  	  user.setCanSellViaHub(obj.get("user").getAsJsonObject().get("can_sell_via_hub").getAsBoolean());
			  	  user.setTooManyCancel(obj.get("user").getAsJsonObject().get("too_many_request_for_cancel_as_seller").getAsBoolean());
			  	  user.setUserType(obj.get("user").getAsJsonObject().get("user_type").getAsString());
			  	  user.setUsername(obj.get("user").getAsJsonObject().get("username").getAsString());
			  	  user.setId(obj.get("user").getAsJsonObject().get("id").getAsInt());
			  	  user.setCountryCode(obj.get("user").getAsJsonObject().get("country_code").getAsString());
			  mk.setSeller(user);
		  }
		  
		  
		  
		return mk;
	}
	
	
	public List<MarketProduct> listMarketProduct(BluePrint bp)
	{
		return listMarketProduct(bp.getExpansion().getId());
	}


	public List<MarketProduct> listMarketProduct(Integer expansionid)
	{
		var ret = new ArrayList<MarketProduct>();
		
		var arr= caches.getCached(MARKETPLACE_PRODUCTS+expansionid, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+MARKETPLACE_PRODUCTS+"?expansion_id="+expansionid);
			}
		}).getAsJsonObject();
		
		arr.entrySet().forEach(id->{
			id.getValue().getAsJsonArray().forEach(obj->{
				var mk = parseMarket(obj.getAsJsonObject());
				ret.add(mk);
			});
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
				
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+BLUEPRINTS+""+(extra!=null?"?"+extra:""));
			}
		}).getAsJsonArray();
	
		List<BluePrint> ret = new ArrayList<>();
		arr.forEach(a->{
			  var obj = a.getAsJsonObject();
			  var b = new BluePrint();
	     	  b.setId(obj.get("id").getAsInt());
	     	  b.setName(obj.get("name").getAsString());
	     	 
	     	  if(!obj.get("version").isJsonNull() && !obj.get("version").getAsString().isBlank())
	     		  b.setVersion(VersionEnum.valueOf(obj.get("version").getAsString().toUpperCase().replace("-", "_")));
	     	  
	     	  
	     	  b.setSlug(obj.get("slug").getAsString());
	     	  b.setMkmId(obj.get("mkm_id").getAsInt());
	     	  b.setScryfallId(obj.get("scryfall_id").getAsString());
	 		  b.setGame(getGameById(obj.get("game_id").getAsInt()));
			  b.setCategorie(getCategoryById(obj.get("category_id").getAsInt()));
			  b.setExpansion(getExpansionById(obj.get("expansion_id").getAsInt()));
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
		return network.doPost(CardTraderConstants.CARDTRADER_API_URI+"/products",obj).get("resource").getAsJsonObject().get("id").getAsInt();
		
	}
	
	
	public void addProductToCart(MarketProduct mk, Boolean ctZero, Integer qty, Address billingAddr, Address shippingAddr) throws IOException
	{
		var obj = new JsonObject(); 
		obj.addProperty("product_id", mk.getId());
		obj.addProperty("quantity", qty);
		obj.addProperty("via_cardtrader_zero", ctZero);
		obj.add("billing_address", json.toJson(billingAddr));
		obj.add("shipping_address",json.toJson(shippingAddr));
		var ret = network.doPost(CardTraderConstants.CARDTRADER_API_URI+"/cart/add",obj);
		
		logger.debug(ret);
		
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
	
	private int pageMin;
	public List<Order> listOrders(Integer page)
	{
		pageMin=page;
		
		if(page==null)
			pageMin=1;
		
		return json.fromJsonList(caches.getCached(ORDERS+pageMin, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+ORDERS+"?limit=100&page="+pageMin).getAsJsonArray();
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
