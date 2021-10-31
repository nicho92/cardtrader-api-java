package org.api.cardtrader.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.api.cardtrader.services.CardTraderService;

public class Test {

	public static void main(String[] args) throws IOException {
		var token = FileUtils.readFileToString(new File("c:/key.txt"), Charset.defaultCharset());
		
		var service = new CardTraderService(token);
		var set = service.getExpansionByCode("M21");
		var bps = service.listBluePrints(service.getCategoryById(1), "Fiery Emancipation",set).get(0);
		
		System.out.println(bps.getId());
		
		
//		service.listMarketProduct(bps).forEach(bp->{
//			System.out.println(bp + " " + bp.getCategorie() +" " + bp.getExpansion());
//		});
		
	}
 
}
