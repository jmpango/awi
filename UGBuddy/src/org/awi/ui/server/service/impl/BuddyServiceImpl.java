package org.awi.ui.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.ui.model.Buddy;
import org.awi.ui.server.adpter.BuddyXmlAdapter;
import org.awi.ui.server.service.BuddyService;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;

public class BuddyServiceImpl implements BuddyService{
	
	private BuddyXmlAdapter buddyXmlAdapter;
	public static BuddyServiceImpl instance;	
	private DocumentBuilder docBuilder;
	
	private BuddyServiceImpl(){
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}
	}
	
	@Override
	public List<Buddy> getBuddies(AssetManager assertManager, String xmlFileName, String parentTag) {
	
		try {
			Document doc = docBuilder.parse(assertManager.open(xmlFileName));
			buddyXmlAdapter = new BuddyXmlAdapter(doc, parentTag);
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	
		
		if(buddyXmlAdapter == null)
			return new ArrayList<Buddy>();
		
		return buddyXmlAdapter.getBuddies();
	}

	@Override
	public List<Buddy> searchBuddies(String searchTerm, List<Buddy> items) {
		int textLength = searchTerm.length();
		List<Buddy> newSearchedDashBoard = new ArrayList<Buddy>();

		if(items != null){
			for (Buddy buddy : items) {

				if (textLength <= buddy.getName().length()) {
					if (searchTerm.equalsIgnoreCase(buddy.getName()
							.substring(0, textLength))) {
						newSearchedDashBoard.add(buddy);
					}
				}
			}
		}
		return newSearchedDashBoard;
	}
	
	public static BuddyServiceImpl getInstance(){
		if(instance == null){
			instance = new BuddyServiceImpl();
		}
		return instance;
	}

}
