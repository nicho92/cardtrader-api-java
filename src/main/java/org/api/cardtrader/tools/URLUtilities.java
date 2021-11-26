package org.api.cardtrader.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.cardtrader.services.CardTraderConstants;
import org.api.cardtrader.services.URLCallListener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class URLUtilities {
	private CloseableHttpClient httpclient;
	private HttpClientContext httpContext;
	private BasicCookieStore cookieStore;
	protected Logger logger = LogManager.getLogger(this.getClass());
	private String bearer;
	private JsonTools json;
	private URLCallListener listener;
	  
	
	public URLUtilities() {
		json = new JsonTools();
		httpclient = HttpClients.custom().setUserAgent(CardTraderConstants.USER_AGENT_VALUE).setRedirectStrategy(new LaxRedirectStrategy()).build();
		httpContext = new HttpClientContext();
		cookieStore = new BasicCookieStore();
		httpContext.setCookieStore(cookieStore);
	
	}
		
	public void initToken(String b)
	{
		this.bearer="Bearer "+b;
	}
	
	
	public String extractAndClose(HttpResponse response) throws IOException
	{
		var ret = EntityUtils.toString(response.getEntity());
		EntityUtils.consume(response.getEntity());
		return ret;
	}
	
	public HttpResponse execute(HttpRequestBase req) throws IOException
	{
		var callInfo = new URLCallInfo();
		Instant start = Instant.now();
		var resp = execute(req);
		Instant stop = Instant.now();
		long duration = stop.toEpochMilli()-start.toEpochMilli();	
	
		callInfo.setResponse(resp);
		callInfo.setStart(start);
		callInfo.setEnd(stop);
		callInfo.setDuration(duration);
		callInfo.setUrl(req.getURI().toASCIIString());
		callInfo.setRequest(req);
		
		if(listener!=null)
			listener.notify(callInfo);
		
		
		
		
		return httpclient.execute(req,httpContext);
	}
	
	public HttpResponse doGet(String url) throws IOException
	{
		return doGet(url,new HashMap<>());
	}
	
	public HttpResponse doPost(String url) throws IOException
	{
		return doPost(url,new HashMap<>());
	}
	
	public JsonObject doPost(String url,JsonElement el) throws IOException
	{
		
		var postReq = new HttpPost(url);
		postReq.addHeader("Authorization",bearer);
		postReq.setEntity(new StringEntity(el.toString(),ContentType.APPLICATION_JSON));
		logger.debug("posting " + el.toString());
		HttpResponse resp = execute(postReq);
		
		String ret = extractAndClose(resp);
		
		if(resp.getStatusLine().getStatusCode()!=200)
			throw new IOException(ret);
		
		
		return json.fromJson(ret).getAsJsonObject();
	
	}
	

	public void doPut(String url, JsonElement el) throws IOException {

		var postReq = new HttpPut(url);
		postReq.addHeader("Authorization",bearer);
		postReq.setEntity(new StringEntity(el.toString(),ContentType.APPLICATION_JSON));
		logger.debug("put " + el.toString());
		HttpResponse resp = execute(postReq);
		
		String ret = extractAndClose(resp);
		
		if(resp.getStatusLine().getStatusCode()!=200)
			throw new IOException(ret);
		
		logger.debug(ret);
		
	}

	

	public void doDelete(String url) throws IOException {
		var delReq = new HttpDelete(url);
		
		HttpResponse resp = execute(delReq);
		String ret = extractAndClose(resp);
		if(resp.getStatusLine().getStatusCode()!=200)
			throw new IOException(ret);
		
		logger.debug(ret);
	}

	
	public HttpResponse doPost(String url,Map<String,String> headers) throws IOException{
		var postReq = new HttpPost(url);

		postReq.addHeader("Authorization",bearer);
		
		if(!headers.isEmpty())
			headers.entrySet().forEach(e->postReq.addHeader(e.getKey(), e.getValue()));
	
		return execute(postReq);
	}
	
	
	
	public HttpResponse doGet(String url,Map<String,String> headers) throws IOException
	{
		logger.info("Parsing url " + url);
		var getReq = new HttpGet(url);
		
		
			getReq.addHeader("Authorization",bearer);
			
			if(headers!=null)
				headers.entrySet().forEach(e->getReq.addHeader(e.getKey(), e.getValue()));
		
		return execute(getReq);
	}

	public void download(String url,File to) throws IOException
	{
		FileUtils.copyInputStreamToFile(doGet(url).getEntity().getContent(),to);
	}
	
	public JsonElement extractJson(String url) throws IOException {
		var reader = new JsonReader(new InputStreamReader(doGet(url).getEntity().getContent()));
		JsonElement e= JsonParser.parseReader(reader);
		logger.trace("return :" + e);
		reader.close();
		return e;
		
		
	}

	public void setCallListener(URLCallListener listener2) {
		this.listener=listener2;
		
	}


}
