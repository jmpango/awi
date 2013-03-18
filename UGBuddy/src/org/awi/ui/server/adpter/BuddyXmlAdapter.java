package org.awi.ui.server.adpter;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.Location;
import org.awi.ui.model.SearchTag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BuddyXmlAdapter {
	
	private static String ID = "id";
	private static String NAME = "name";
	private static String ADDRESS = "address";
	private static String TAG_LINE = "tagline";
	private static String EMAIL = "email";
	private static String URL = "url";
	private static String TELPHONE = "telphone";
	private static String LOCATIONS_TAG = "locations";
	private static String LOCATION = "location";
	private static String SEARCH_TAGS = "searchtags";
	private static String SEACH_TAG = "searchtag";

	private NodeList buddyNodeList;
	private Document document;
	private List<Buddy> buddies;

	public BuddyXmlAdapter(Document document, String parentTag) {
		this.document = document;
		this.buddies = new ArrayList<Buddy>();

		buddyNodeList = document.getElementsByTagName(parentTag);
		document.getDocumentElement().normalize();
	}

	public List<Buddy> getBuddies() {
		Buddy buddy = null;

		for (int i = 0; i < buddyNodeList.getLength(); i++) {
			buddy = new Buddy();
			
			Node node = buddyNodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element parentElement = (Element) node;
				
				NodeList  idNode = parentElement.getElementsByTagName(ID);
				Element idElement = (Element) idNode.item(0);
				NodeList idList = idElement.getChildNodes();
				
				buddy.setId(((Node)idList.item(0)).getNodeValue().trim());
				
				NodeList  nameNode = parentElement.getElementsByTagName(NAME);
				Element nameElement = (Element) nameNode.item(0);
				NodeList nameList = nameElement.getChildNodes();
				
				buddy.setName(((Node)nameList.item(0)).getNodeValue().trim());
				
				NodeList  buddyLocationNode = parentElement.getElementsByTagName(ADDRESS);
				Element buddyLocationElement = (Element) buddyLocationNode.item(0);
				NodeList buddyLocationList = buddyLocationElement.getChildNodes();
				
				buddy.setAddress(((Node)buddyLocationList.item(0)).getNodeValue().trim());
				
				NodeList  taglineNode = parentElement.getElementsByTagName(TAG_LINE);
				Element taglineElement = (Element) taglineNode.item(0);
				NodeList taglineList = taglineElement.getChildNodes();
				
				buddy.setTagLine(((Node)taglineList.item(0)).getNodeValue().trim());
				
				NodeList  emailNode = parentElement.getElementsByTagName(EMAIL);
				Element emailElement = (Element) emailNode.item(0);
				NodeList emailList = emailElement.getChildNodes();
				
				Node emailNodex = ((Node)emailList.item(0));
				if(emailNodex != null )
				buddy.setEmail(emailNodex.getNodeValue().trim());
				
				NodeList  urlNode = parentElement.getElementsByTagName(URL);
				Element urlElement = (Element) urlNode.item(0);
				NodeList urlList = urlElement.getChildNodes();
				
				Node urlNodex = ((Node)urlList.item(0));
				if(urlNodex != null)
				buddy.setUrl(urlNodex.getNodeValue().trim());
				
				NodeList  telNode = parentElement.getElementsByTagName(TELPHONE);
				Element telElement = (Element) telNode.item(0);
				NodeList telList = telElement.getChildNodes();
				
				buddy.setTelphoneNos(((Node)telList.item(0)).getNodeValue().trim());
				
				NodeList  locationsNodeList = parentElement.getElementsByTagName(LOCATIONS_TAG);
				List<Location> locationList = new ArrayList<Location>();
				Location loc = null;
				for(int k = 0; k < locationsNodeList.getLength(); k++){
					loc = new Location();
					
					Node locationNode = locationsNodeList.item(k);
					if (locationNode.getNodeType() == Node.ELEMENT_NODE) {
						Element _firstItemElement = (Element) locationNode;
						NodeList _nameList = _firstItemElement.getElementsByTagName(LOCATION);
						Element locNameElement = (Element) _nameList.item(0);
						NodeList locName = locNameElement.getChildNodes();
						loc.setName(((Node) locName.item(0)).getNodeValue().trim());
						locationList.add(loc);
					}
				}
				
				buddy.setLocations(locationList);
				
				NodeList  searchTagNodeList = parentElement.getElementsByTagName(SEARCH_TAGS);
				List<SearchTag> searchTagList = new ArrayList<SearchTag>();
				SearchTag searchTag = null;
				for(int j = 0; j < searchTagNodeList.getLength(); j++){
					searchTag = new SearchTag();
					
					Node searchTagNode = searchTagNodeList.item(j);
					if (searchTagNode.getNodeType() == Node.ELEMENT_NODE) {
						Element searchFirstItemElement = (Element) searchTagNode;
						NodeList _nameSearchList = searchFirstItemElement.getElementsByTagName(SEACH_TAG);
						Element searchTagNameElement = (Element) _nameSearchList.item(0);
						NodeList searchTagName = searchTagNameElement.getChildNodes();
						searchTag.setName(((Node) searchTagName.item(0)).getNodeValue().trim());
						searchTagList.add(searchTag);
					}
				}
				
				buddy.setSearchTags(searchTagList);
				
				buddies.add(buddy);
			}
		}

		return buddies;
	}

	public Document getDocument() {
		return document;
	}

}
