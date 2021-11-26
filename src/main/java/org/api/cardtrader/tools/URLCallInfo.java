package org.api.cardtrader.tools;

import java.io.Serializable;
import java.time.Instant;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

public class URLCallInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Instant start;
	private long duration;
	private Instant end;
	private String url;
	private HttpResponse response;
	private HttpRequestBase request;

	@Override
	public String toString() {
		return request.toString();
	}

	public Instant getStart() {
		return start;
	}

	public void setStart(Instant start) {
		this.start = start;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Instant getEnd() {
		return end;
	}

	public void setEnd(Instant end) {
		this.end = end;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public HttpRequestBase getRequest() {
		return request;
	}

	public void setRequest(HttpRequestBase request) {
		this.request = request;
	}
	
	

}
