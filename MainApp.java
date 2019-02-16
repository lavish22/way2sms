package com.way2sms;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class MainApp {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("DO you want to update the CUSTOMERS_INFO table? y/n");
		String choice = sc.next();
		
		DataParser dp = new DataParser();	//this will parse one xml file which have mobno and name in it
		Map<String,String> data = null;
		
		if( choice.equals("y") || choice.equals("Y") ) {
			dp.ParseAndSave();	   //and add it to DB in way2sms.CUSTOMERS_INFO
		}
		data=dp.getData();
		Way2SmsMain call = new Way2SmsMain();
		try {
		Properties prop = new Properties();
		InputStream input = new FileInputStream("/home/shahzeb/eclipse-workspace/Way2Sms/src/config.properties");
		prop.load(input);
		
			for (Map.Entry<String,String> entry : data.entrySet()) {
				String mobno = entry.getKey();
				String name = entry.getValue(); 
				String msg =("hi "+name+", this is a test Message!");
				//System.out.println(msg);
    		String res= call.sendCampaign(
					prop.getProperty("apikey"),
					prop.getProperty("secret"),
					prop.getProperty("usetype"),
					mobno,
					msg,
					prop.getProperty("senderid")
					);
			Thread.sleep(1000);
			System.out.println(res+mobno);
			}
		}
		catch(IOException ex) {
			System.out.println(ex);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
}

