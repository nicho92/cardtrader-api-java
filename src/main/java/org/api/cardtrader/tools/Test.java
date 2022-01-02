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
		
		service.listOrders(1).forEach(o->{
			
			System.out.println(o);
			
		});
		
//		var set = service.getExpansionByCode("A25");
//		
//		
//		var bpS = service.listBluePrints(null, "Chalice of the Void",set).get(0);
//		System.out.println(bpS.getCategorie() + " " + bpS.getName() + " " + bpS.getExpansion() +" " + bpS.getId());
//		
//		
//		service.listMarketProductByBluePrint(bpS).forEach(bp->{
//			System.out.println(bp + " " + bp.getCategorie() +" " + bp.getExpansion() +" " + bp.getPrice() +" " + bp.getSeller() + " " + bp.getSeller().getCountryCode());
//		});
//		
		
		
	}
 
}
