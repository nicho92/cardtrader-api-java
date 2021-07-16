package org.api.cardtrader.tools;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.cardtrader.services.CardTraderConstants;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class URLUtilities {
	private CloseableHttpClient httpclient;
	private HttpClientContext httpContext;
	private BasicCookieStore cookieStore;
	protected Logger logger = LogManager.getLogger(this.getClass());
	private String bearer;
	
	public URLUtilities() {
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
		return httpclient.execute(req,httpContext);
	}
	
	public HttpResponse doGet(String url) throws IOException
	{
		return doGet(url,null);
	}
	
	public HttpResponse doGet(String url,Map<String,String> headers) throws IOException
	{
		logger.debug("Parsing url " + url);
		var getReq = new HttpGet(url);
		
		
			getReq.addHeader("Authorization",bearer);
			
			if(headers!=null)
				headers.entrySet().forEach(e->getReq.addHeader(e.getKey(), e.getValue()));
		
		return execute(getReq);
	}

	

	public JsonElement extractJson(String url) throws IOException {
		var reader = new JsonReader(new InputStreamReader(doGet(url).getEntity().getContent()));
		JsonElement e= JsonParser.parseReader(reader);
		logger.debug("return :" + e);
		reader.close();
		return e;
		
		
	}


}
