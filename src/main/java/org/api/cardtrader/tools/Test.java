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
		
//		service.listMarketProduct(1).forEach(bp->{
//			System.out.println(bp + " " + bp.getCategorie() +" " + bp.getExpansion());
//		});
		
		service.listOrders(1).forEach(od->{
				System.out.println(od.getId() + " " +  od.getBuyer()+ "  "+  od.getOrderItems());
		});
		
		
	}
 
}
