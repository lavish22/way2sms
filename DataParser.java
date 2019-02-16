package com.way2sms;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataParser {
	
	private Connection conn = null;
	
	public Connection openConnection() {
		try {
			if(conn==null) {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		        //Reference to connection interface
		        this.conn = DriverManager.getConnection("jdbc:mysql://localhost/way2sms?useSSL=false&" +
		                "user="+"lavish"+"&password="+"lavish");
			}
			return conn;
		}
		catch(SQLException ex) {
			System.out.println(ex);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return conn;
	}						//end of OpenConnection
	
	@SuppressWarnings("deprecation")
	public void ParseAndSave(){
	    try {
	     	Connection conn = openConnection();
	       	File fXmlFile = new File("/home/shahzeb/Downloads/data.xml");
	       	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	       	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	       	Document doc = dBuilder.parse(fXmlFile);
	      			
	       	doc.getDocumentElement().normalize();
	      			
	       	NodeList nList = doc.getElementsByTagName("record");
        	for (int temp = 0; temp < nList.getLength(); temp++) {
        		Node nNode = nList.item(temp);
        				
        		//System.out.println("\nCurrent Element :" + nNode.getNodeName());
       				
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        			Element eElement = (Element) nNode;
       			
        			String query = "INSERT INTO CUSTOMERS_INFO VALUES('"+eElement.getElementsByTagName("MOBNO").item(0).getTextContent().replace("-","")+"','"+ 
        			eElement.getElementsByTagName("NAME").item(0).getTextContent()+"');";
	        			
        			//System.out.println(query);
	        			
        			Statement st  = conn.createStatement();
        	        boolean m = st.execute(query);
	        	}
        	}
        } catch(SQLException e){e.printStackTrace();}
	    catch( Exception e ) {
	    	e.printStackTrace();
	    }
	}							//end of parseAndSave
	
	public Map<String,String> getData(){
		Map<String, String> mymap = new HashMap<String,String>();
		try {
			Connection conn = openConnection();
			String query = "SELECT mobno,name FROM way2sms.CUSTOMERS_INFO;";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
    			mymap.put( rs.getString("mobno"),rs.getString("name") );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mymap;
	}						//end of getData
}
