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
		
		service.listBluePrintsByExpansion(3181).forEach(o->{
			System.out.println(o.getId() + " " + o.getName() +" " + o.getSlug() + " " + o.getScryfallId());
		});
		
		System.out.println(service.getBluePrintById(236462).getSlug());
	}
 
}
