package org.api.cardtrader.tools;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JsonTools {

	protected Logger logger = LogManager.getLogger(this.getClass());

	private Gson gson;
	
	public JsonTools() {
		gson = new GsonBuilder().create();
	}
	
	
	public <T> List<T> fromJsonList(JsonElement s,Class<T> classe)
	{
		ArrayList<T> list = new ArrayList<>();
		var json= gson.fromJson(s,JsonArray.class);
		json.forEach(el->list.add(fromJson(el,classe)));
		return list;
	}

	public <T> T fromJson(JsonElement info, Class<T> class1) {
		return gson.fromJson(info, class1);
	}
	
	public JsonElement toJson(Object o)
	{
		return gson.toJsonTree(o);
	}
	
	public JsonElement fromJson(String o)
	{
		return gson.fromJson(new StringReader(o), JsonElement.class);
	}

	public Date toDate(JsonElement content)
	{
		if(content==null || content.isJsonNull())
			return null;
		
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(content.getAsString());
		} catch (ParseException e) {
			logger.error(e);
			return null;
		}
		
	}
	


}
