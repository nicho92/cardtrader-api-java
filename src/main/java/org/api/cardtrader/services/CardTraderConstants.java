package org.api.cardtrader.services;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CardTraderConstants {
	
	public static final String CARDTRADER_API_BASE_URI ="https://api.cardtrader.com/api";
	public static final String CARDTRADER_API_VERSION ="v2";
	public static final String CARDTRADER_API_URI =CARDTRADER_API_BASE_URI+"/"+CARDTRADER_API_VERSION;
	public static final String SHARE_CODE="black-forest-25228";
	public static final String API_FORMAT="json";
	public static final String CARDTRADER_WEBSITE_URI="https://www.cardtrader.com/";
	public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
	public static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
	public static final String CARDTRADER_JAVA_API_VERSION = "0.0.21";
}
