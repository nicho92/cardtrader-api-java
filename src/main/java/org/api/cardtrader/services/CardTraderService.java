package org.api.cardtrader.services;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.api.cardtrader.enums.ConditionEnum;
import org.api.cardtrader.enums.Identifier;
import org.api.cardtrader.enums.StateEnum;
import org.api.cardtrader.enums.VersionEnum;
import org.api.cardtrader.modele.Address;
import org.api.cardtrader.modele.App;
import org.api.cardtrader.modele.BluePrint;
import org.api.cardtrader.modele.Categorie;
import org.api.cardtrader.modele.Expansion;
import org.api.cardtrader.modele.Game;
import org.api.cardtrader.modele.MarketProduct;
import org.api.cardtrader.modele.Order;
import org.api.cardtrader.modele.OrderItem;
import org.api.cardtrader.modele.Price;
import org.api.cardtrader.modele.ShippingMethod;
import org.api.cardtrader.modele.User;
import org.api.cardtrader.tools.CacheManager;
import org.api.cardtrader.tools.JsonTools;
import org.api.cardtrader.tools.URLUtilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CardTraderService {

	private static final String MARKETPLACE_PRODUCTS = "marketplace/products";
	private static final String ORDERS = "orders";
	private static final String BLUEPRINTS = "blueprints";
	private static final String EXPANSIONS = "expansions";
	private static final String CATEGORIES = "categories";
	private static final String GAMES = "games";
	private static final String MARKETPLACE_BLUEPRINTS = "marketplace/blueprints";
	
	private JsonTools json;
	private URLUtilities network; 
	private String token;
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	private boolean forceExpansionLoadingIfNotFound=true;
	private CacheManager<JsonElement> caches;
	private App app;
	
	public CardTraderService(String token) {
		json = new JsonTools();
		network = new URLUtilities();
		network.initToken(token);
		this.token=token;
		caches = new CacheManager<>();
		forceExpansionLoadingIfNotFound = true;
	}
	
	public void setForceExpansionLoadingIfNotFound(boolean forceExpansionLoadingIfNotFound) {
		this.forceExpansionLoadingIfNotFound = forceExpansionLoadingIfNotFound;
	}
	
	
	public void enableCaching(boolean enable)
	{
		caches.setEnable(enable);
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
	
	public List<Expansion> listExpansions(int idGame)
	{
		List<Expansion> ret = json.fromJsonList(caches.getCached(EXPANSIONS+idGame, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+EXPANSIONS+"?game_id="+idGame+"?format=json").getAsJsonArray();
			}
		}),Expansion.class);
		ret.forEach(ex->ex.setGame(getGameById(idGame)));
		return ret;
	}
	
	
	public List<Expansion> listExpansions()
	{
		List<Expansion> ret = json.fromJsonList(caches.getCached(EXPANSIONS, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+EXPANSIONS+"?format=json").getAsJsonArray();
			}
		}),Expansion.class);
		ret.forEach(ex->ex.setGame(getGameById(ex.getGameId())));
		return ret;
	}
	
	public List<Game> listGames()
	{
			return json.fromJsonList(caches.getCached(GAMES, new Callable<JsonElement>() {
				@Override
				public JsonElement call() throws Exception {
					return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+GAMES+"?format=json").getAsJsonObject().get("array").getAsJsonArray();
				}
			}),Game.class);
	}
	
	public List<Categorie> listCategories()
	{
		List<Categorie> ret= json.fromJsonList(caches.getCached(CATEGORIES, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+CATEGORIES+"?format=json").getAsJsonArray();
			}
		}),Categorie.class);
		
		
		ret.forEach(ex->ex.setGame(getGameById(ex.getGameId())));

		
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
	
	public Expansion getExpansionByName(String name) {
		return listExpansions().stream().filter(c->c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	
	public Expansion getExpansionByCode(String code) {
		return listExpansions().stream().filter(c->c.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
	}
	
	
	public List<MarketProduct> listMarketProduct(Expansion exp){
		return listMarketProductByExpansion(exp.getId()); 
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
	

	public List<MarketProduct> listMarketProductByBluePrint(BluePrint bp)
	{
		return listMarketProductByBluePrint(bp.getId(),bp.getCategorie());
	}


	private MarketProduct parseMarket(JsonObject obj) {
		var mk  = new MarketProduct();
		mk.setIdBlueprint(obj.get("blueprint_id").getAsInt());
		mk.setId(obj.get("id").getAsInt());			

		if(!obj.get("description").isJsonNull())
		 mk.setDescription(obj.get("description").getAsString());
		
		if(obj.get("category_id")!=null)
			mk.setCategorie(getCategoryById(obj.get("category_id").getAsInt()));
		
		if(obj.get("game_id")!=null)
			mk.setGame(getGameById(obj.get("game_id").getAsInt()));
		
		
		if(obj.get("expansion")!=null && obj.get("expansion").isJsonObject())
		{
			mk.setExpansion(getExpansionById(obj.get("expansion").getAsJsonObject().get("id").getAsInt()));
		}
		else if(forceExpansionLoadingIfNotFound)// value is not filled in fab game.
		{
			mk.setExpansion(listMarketProductByBluePrint(mk.getIdBlueprint(),mk.getCategorie()).get(0).getExpansion());
		}
		
		mk.setQty(obj.get("quantity").getAsInt());
	
		if (obj.get("price_cents") != null)
			mk.setPrice(new Price(Double.valueOf((obj.get("price_cents").getAsInt() / 100.0)),obj.get("price_currency").getAsString()));

		if (obj.get("bundled_quantity") != null)
			mk.setBundledQuantity(obj.get("bundled_quantity").getAsInt());

		if (obj.get("name_en") != null)
			mk.setName(obj.get("name_en").getAsString());

		if (obj.get("name") != null)
			mk.setName(obj.get("name").getAsString());

		if (obj.get("bundle") != null)
			mk.setBundle(obj.get("bundle").getAsBoolean());

		if (obj.get("graded") != null && !obj.get("graded").isJsonNull())
			mk.setGraded(obj.get("graded").getAsBoolean());

		if (obj.get("properties_hash").getAsJsonObject().get("mtg_foil") != null)
			mk.setFoil(obj.get("properties_hash").getAsJsonObject().get("mtg_foil").getAsBoolean());

		if (obj.get("properties_hash").getAsJsonObject().get("fab_foil") != null)
			mk.setFoil(obj.get("properties_hash").getAsJsonObject().get("fab_foil").getAsBoolean());

		if (obj.get("properties_hash").getAsJsonObject().get("signed") != null)
			mk.setSigned(obj.get("properties_hash").getAsJsonObject().get("signed").getAsBoolean());

		if (obj.get("properties_hash").getAsJsonObject().get("altered") != null)
			mk.setAltered(obj.get("properties_hash").getAsJsonObject().get("altered").getAsBoolean());

		if (obj.get("properties_hash").getAsJsonObject().get("fab_language") != null)
			mk.setLanguage(obj.get("properties_hash").getAsJsonObject().get("fab_language").getAsString());

		if (obj.get("properties_hash").getAsJsonObject().get("mtg_language") != null)
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
	
	
	
	private List<MarketProduct> listMarketProductByBluePrint(Integer idBlueprint, Categorie ct) {
		var ret = new ArrayList<MarketProduct>();
		var arr= caches.getCached(MARKETPLACE_BLUEPRINTS+idBlueprint, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+MARKETPLACE_PRODUCTS+"?blueprint_id="+idBlueprint);
			}
		}).getAsJsonObject();
		
		arr.entrySet().forEach(id->{
			id.getValue().getAsJsonArray().forEach(obj->{
				var mk = parseMarket(obj.getAsJsonObject());
				mk.setCategorie(ct);
				ret.add(mk);
			});
		});
		return ret;
	}


	public List<MarketProduct> listMarketProductByExpansion(Expansion expansion)
	{
		return listMarketProductByExpansion(expansion.getId());
	}

	public List<MarketProduct> listMarketProductByExpansion(Integer expansionid)
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
	
	public List<BluePrint> listBluePrints(@Nonnull String name, Expansion set)
	{
		if(set==null)
			return listBluePrintsByName(name, null);
		
		return  listBluePrintsByName(name, set.getId());
	}
	
	
	public List<BluePrint> listBluePrintsByName(@Nonnull String name, Integer idSet)
	{
		var arr= caches.getCached(BLUEPRINTS+name, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+BLUEPRINTS+"/?name="+URLEncoder.encode(name,"UTF-8") + (idSet!=null?"expansion_id="+idSet:"")).getAsJsonArray();
			}
		});
		
		var ret = new ArrayList<BluePrint>();
		arr.getAsJsonArray().forEach(je->{
			listBluePrintsByExpansion(je.getAsJsonObject().get("expansion_id").getAsInt()).forEach(bp->{
				if(bp.getName().equals(name))
					ret.add(bp);
			});
		});
		return ret;
		
		
	}
	
	public List<BluePrint> listBluePrintsByExpansion(Expansion expansion)
	{
		return listBluePrintsByExpansion(expansion.getId());
	}
	
	public List<BluePrint> listBluePrintsByExpansion(Integer expansionid)
	{
		
		var arr= caches.getCached(BLUEPRINTS+expansionid, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				
				String extra=null;
				
				if(expansionid!=null)
				{
					extra+="&expansion_id="+expansionid;
				}
				
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+BLUEPRINTS+"/export"+(extra!=null?"?"+extra:""));
			}
		}).getAsJsonArray();
		
		
		return parseBluePrint(arr);
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
		var ret = new ArrayList<Order>();
		
		
		if(page==null)
			pageMin=1;
		
		var arr = caches.getCached(ORDERS+pageMin, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+ORDERS+"?format=json&limit=100&page="+pageMin).getAsJsonArray();
			}
		}).getAsJsonArray();
	
		arr.forEach(je->ret.add(parseOrder(je.getAsJsonObject())));
		
		return ret;
		
		
	}

	public Order getOrderDetails(int idOrder)
	{
		
		return  parseOrder(caches.getCached(ORDERS+idOrder, new Callable<JsonElement>() {
			@Override
			public JsonElement call() throws Exception {
				return network.extractJson(CardTraderConstants.CARDTRADER_API_URI+"/"+ORDERS+"/"+idOrder+"?format=json").getAsJsonObject();
			}
		}).getAsJsonObject());
	}

	private List<BluePrint> parseBluePrint(JsonArray arr) {
		List<BluePrint> ret = new ArrayList<>();
		arr.forEach(a->{
			  var obj = a.getAsJsonObject();
			  
			 
			  var b = new BluePrint();
	     	  b.setId(obj.get("id").getAsInt());
	     	  b.setName(obj.get("name").getAsString());
	     	  
	     	  try {
		     	  if(!obj.get("version").isJsonNull() && !obj.get("version").getAsString().isBlank())
		     		  b.setVersion(VersionEnum.valueOf(obj.get("version").getAsString().toUpperCase().replace("-", "_").replace(" ", "_")));
	     	  }
	     	  catch(IllegalArgumentException ex)
	     	  {
	     		  logger.trace(ex.getMessage());
	     	  }
	     	  
	     	 if(obj.get("slug")!=null && !obj.get("slug").isJsonNull())
	     		 b.setSlug(obj.get("slug").getAsString());
	     	  
	     	  if(obj.get("mkm_id")!=null && !obj.get("mkm_id").isJsonNull())
	     		  b.setMkmId(obj.get("mkm_id").getAsInt());
	     	  
	    	  if(obj.get("scryfall_id")!=null && !obj.get("scryfall_id").isJsonNull())
	     		  b.setScryfallId(obj.get("scryfall_id").getAsString());
	     	  
	     	  
	 		  b.setGame(getGameById(obj.get("game_id").getAsInt()));
			  b.setCategorie(getCategoryById(obj.get("category_id").getAsInt()));
			  b.setExpansion(getExpansionById(obj.get("expansion_id").getAsInt()));
			  b.setProductUrl(CardTraderConstants.CARDTRADER_WEBSITE_URI+"cards/"+b.getSlug()+"?share_code="+CardTraderConstants.SHARE_CODE);
			  
			  if(obj.get("fixed_properties")!=null && obj.get("fixed_properties").getAsJsonObject().get("collector_number")!=null)
			  {
				  try {
					  b.setCollectorNumber(obj.get("fixed_properties").getAsJsonObject().get("collector_number").getAsString());
				  }
				  catch(Exception e)
				  {
					  //do nothing
				  }
			  
			  }
			  
			  if(obj.get("image")!=null && !obj.get("image").isJsonNull())
			  {
				  b.setImageUrl(CardTraderConstants.CARDTRADER_WEBSITE_URI+obj.get("image").getAsJsonObject().get("url").getAsString());
			  }
			  else
			  {
				  b.setImageUrl("https://api.scryfall.com/cards/"+b.getScryfallId()+"?format=image");
			  }
			  ret.add(b);
		});
		return ret;
	}

	
	private Order parseOrder(JsonObject je) {
		var o  = new Order();
			  o.setId(je.get("id").getAsInt());
			  o.setCode(je.get("code").getAsString());
			  o.setOrderAs(je.get("order_as").getAsString());
			  o.setTransactionCode(je.get("transaction_code").getAsString());
			  o.setState(StateEnum.parseByLabel(je.get("state").getAsString()));
			  o.setSize(je.get("size").getAsInt());
			  o.setPackagingNumber(je.get("packing_number").getAsInt());
			  o.setPresale(!je.get("presale").isJsonNull() && je.get("presale").getAsBoolean());
			  o.setDateCreation(json.toDate(je.get("paid_at")));
			  o.setDatePresaleEnd(json.toDate(je.get("presale_ended_at")));
			  o.setDateCancel(json.toDate(je.get("cancelled_at")));
			  o.setDatePaid(json.toDate(je.get("paid_at")));
			  o.setDateSend(json.toDate(je.get("sent_at")));  
			  o.setDateCancel(json.toDate(je.get("cancelled_at")));
			  
			  o.setTotal(new Price(je.get(o.getOrderAs()+"_total").getAsJsonObject().get("cents").getAsInt()/100.0, je.get(o.getOrderAs()+"_total").getAsJsonObject().get("currency").getAsString()));
			  o.setSubTotal(new Price(je.get(o.getOrderAs()+"_subtotal").getAsJsonObject().get("cents").getAsInt()/100.0, je.get(o.getOrderAs()+"_subtotal").getAsJsonObject().get("currency").getAsString()));
			  
			  if(o.getOrderAs().equals("seller"))
				  o.setSellerFeeAmount(new Price(je.get(o.getOrderAs()+"_fee_amount").getAsJsonObject().get("cents").getAsInt()/100.0, je.get(o.getOrderAs()+"_fee_amount").getAsJsonObject().get("currency").getAsString()));
			  
			  
			  
			  
			  var jeUser = je.get("seller").getAsJsonObject();
			  o.setSeller(new User(jeUser.get("id").getAsInt(), jeUser.get("username").getAsString()));
			
			  if(je.get("buyer")!=null)
			  {
				  jeUser = je.get("buyer").getAsJsonObject();
				  o.setBuyer(new User(jeUser.get("id").getAsInt(), jeUser.get("username").getAsString(), jeUser.get("email").getAsString(), String.valueOf(jeUser.get("phone"))));
			  }	
			  	
			  o.setShippingAddress(parseAddress( je.get("order_shipping_address").getAsJsonObject()));
			  o.setBillingAddress(parseAddress( je.get("order_billing_address").getAsJsonObject()));
			  
			  
			  if(je.get("order_shipping_method")!=null)
			  {
				  var joShip = je.get("order_shipping_method").getAsJsonObject();
				  
				  ShippingMethod m = new ShippingMethod();
				  					       m.setId(joShip.get("id").getAsInt());
				  					       m.setName(joShip.get("name").getAsString());
				  					       m.setTracked(joShip.get("tracked").getAsBoolean());
				  					       m.setTrackedCode(joShip.get("tracking_code").isJsonNull()?"":joShip.get("tracking_code").getAsString());
				  					       m.setEstimatedShippingDay(joShip.get("max_estimate_shipping_days").getAsInt());
				  					       
				  					       var jop = joShip.get(o.getOrderAs()+"_price").getAsJsonObject();
				  					       m.setSellerPrice(new Price(jop.get("cents").getAsInt()/100.0, jop.get("currency").getAsString()));
				  					       
				  o.setShippingMethod(m);
			  }
			  
			  
			  List<OrderItem> list = json.fromJsonList(je.get("order_items"), OrderItem.class);
			  for(var item : list)
			  {
				  
				item.getPrice().setValue(item.getPrice().getValue()/100.0);
				item.setFoil(item.getProperties().getOrDefault("mtg_foil","false").equals("true"));
				item.setSigned(item.getProperties().getOrDefault("signed","false").equals("true"));
				item.setAltered(item.getProperties().getOrDefault("altered","false").equals("true"));
				item.setLang(item.getProperties().getOrDefault("mtg_language",""));
				item.setExpansionProduct(getExpansionByName(item.getExpansion()));
				
				item.setCondition(ConditionEnum.parseByLabel((item.getProperties().getOrDefault("condition","Near Mint"))));
			  }
			  
			  
			  o.setOrderItems(list);
			  
			  
			  		
			  
		return o;
	}

	private Address parseAddress(JsonObject obj) {
		
		var add = new Address();
			  add.setId(obj.get("id").getAsInt());
			  add.setName(obj.get("name").getAsString());
			  add.setStreet(obj.get("street").getAsString());
			  add.setZip(obj.get("zip").getAsString());
			  add.setCity(obj.get("city").getAsString());
			  add.setStateOrProvince(obj.get("state_or_province").getAsString());
			  add.setCountryCode(obj.get("country_code").getAsString());
			  add.setVatNumber(obj.get("vat_number").isJsonNull()?"": obj.get("vat_number").getAsString());
			  add.setCountry(obj.get("country").getAsString());
			  
			  
		
		return add;
	}

	public void setListener(URLCallListener urlCallListener) {
		network.setCallListener(urlCallListener);
		
	}


	
	
}
