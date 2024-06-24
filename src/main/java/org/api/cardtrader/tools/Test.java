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
		
		var bpCard = service.listBluePrintsByName("Sol Ring",3181).get(0);
		
		
		service.listMarketProductByBluePrint(bpCard).forEach(mp->{
			
			System.out.println(bpCard.getName() + " " + mp.getQty() + " " + mp.getLanguage() + " " + mp.getPrice() + " " + mp.getSeller());
			
		});
		
		
	}
 
}
