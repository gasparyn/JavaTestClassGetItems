import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import us.monoid.web.Resty;
import us.monoid.web.XMLResource;


public class MainClass {
	
	//vars
	static User user1=null;
	static NodeList itList;
	static NodeList nList = null;
	static ArrayList<User> ok = null;
	static Resty resty1 = new Resty();
	static Resty resty2 = new Resty();
	static XMLResource itm1=null;
	static XMLResource usr1 = null;
	static int id = 0;
	//static ArrayList<Item> sometester =new ArrayList<Item>();
	
	/**
	 * This method returns all the users currently regstered in our server
	 * @return -list of all users in database
	 */
	public static ArrayList<User> getAllUsers(){
		try {
			usr1 = resty1.xml("http://vogueable.heroku.com/users.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			String st = ""+usr1;
			InputStream is = new ByteArrayInputStream(st.getBytes());

			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName("user");

		} catch (IOException ex) {
			ex.printStackTrace();
			//Log.e("gaspar", "exception on r.xml");
		} catch (ParserConfigurationException ex1) {
			ex1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		System.out.println("Name  \t\t\t\tYour ID");
		ArrayList<User> users=new ArrayList<User>();
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			//int serverUserID;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				//String user="";
				id=Integer.parseInt(getTagValue("id",eElement));
				users.add(new User(getTagValue("user-name",eElement),getTagValue("email", eElement),id));
				ok=users;	
			}
		}
		user1=ok.get(0);
		System.out.println("Mr/Mrs "+user1.getName()+" \t\t"+user1.getID());

		return users;

	}
	/**
	 * This method will return a batch of items of certain department(s) that a given user has not viewed
	 * @param args
	 */
	public static  ArrayList<Item> getAllItems(User user,int depts,int batch){
		try {
			int batchSize=1;
			
			//if (!depts.isEmpty()){	
				//check no of dept selected
			 //  for(Department d:depts){
				   String link="http://vogueable.heroku.com/find.xml?user="+user.getID()+"&dept="+depts+"&batch="+batch;
				   itm1 = resty2.xml("http://vogueable.heroku.com/find.xml?user=127&dept=2&batch=20");
				   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				   DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				   String st = ""+itm1;
				   InputStream is = new ByteArrayInputStream(st.getBytes());
				   Document doc = dBuilder.parse(is);
				   doc.getDocumentElement().normalize();
				   itList = doc.getElementsByTagName("item");	
				   //sometester=itList;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			//Log.e("gaspar", "exception on r.xml");
		} catch (ParserConfigurationException ex1) {
			ex1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		ArrayList<Item> items = new ArrayList<Item>();
		if(itList.getLength()>0)
			for (int temp = 0; temp < itList.getLength(); temp++) {

				Node nNode = itList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Item it = new Item("it");
					Element eElement = (Element) nNode;
					it.setName(getTagValue("name", eElement));
					//	Log.d(TAG,"name" + it.getName());
					it.setImageFileString(getTagValue("img-url", eElement));
					it.setDescription(getTagValue("features", eElement));
					it.setLink(getTagValue("link-to-buy", eElement));
					it.setPrice(getTagValue("item-price", eElement));
					it.setCategory(getTagValue("category", eElement));
					it.setBrand(getTagValue("brand", eElement));
					it.setBrand(getTagValue("id", eElement));
					it.addTag(getTagValue("fabric-type", eElement));
					items.add(it);

				}
			}
		//Item item1=items.get(0);
		System.out.println("Item ID\t\t Description \t\t Name");
		for(Item i:items){
			System.out.println(i.getName()+" "+i.getDescription()+""+i.getPrice());
		}
	return items;
		
	}
	public static String getTagValue(String sTag, Element eElement) {
		NodeList list = eElement.getElementsByTagName(sTag);
		Node el = list.item(0);
		//Log.d(TAG,"error on el");

		if (el != null) {
			NodeList nlList = el.getChildNodes();//get all children of the item node
			Node nValue = (Node) nlList.item(0);
			if (nValue != null){
				return nValue.getNodeValue();
			}
		}
		return null; 
	}

	public static void main(String[] args){
		User user=new User("Guy Maharaj","guy@vogueable.com",2);
//		ArrayList<Department> dept= new ArrayList<Department>();
//		Department d1,d2,d3;
//		d1=new Department("Dresses");
//		d2=new Department("Shoes");
//		d3=new Department("Accesories");
//	    dept.add(d1);
//	    dept.add(d2);
//	    dept.add(d3);
		for (Item t:getAllItems(user,3,30)){
    		System.out.println("Name: "+t.getName()+" Price "+t.getPrice());
    		}
		
		for(User s:getAllUsers()){
			System.out.println("Name: "+s.getName()+" System ID "+s.getID());
		       if (s.getName()==("Guy Maharaj")){
		    	//}
		    }
		  }
		} 
			
	}	
	