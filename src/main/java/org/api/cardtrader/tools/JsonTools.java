package org.api.cardtrader.tools;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JsonTools {

	
	private Gson gson;
	
	public JsonTools() {
		gson = new Gson();
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
}
