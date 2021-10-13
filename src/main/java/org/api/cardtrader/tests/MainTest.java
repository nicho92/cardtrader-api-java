package org.api.cardtrader.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.api.cardtrader.services.CardTraderService;

public class MainTest {

	public static void main(String[] args) throws IOException {
		CardTraderService serv = new CardTraderService(FileUtils.readFileToString(new File("c:/key.txt"),Charset.defaultCharset()));
		
		serv.listMarketProduct(17).forEach(c->
			System.out.println(c.getNameEn() + " " + c.getExpansion() + " "+  c.getPrice() + " " + c.getCondition() + " " + c.getSeller())
		);
		
		
	}

}
