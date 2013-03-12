package org.awi.buddy.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.buddy.model.Buddy;
import org.awi.buddy.server.adapter.BuddyXmlAdapter;
import org.awi.buddy.server.service.BuddyService;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;

public class BuddyServiceImpl implements BuddyService{
	
	private BuddyXmlAdapter buddyXmlAdapter;
	public static BuddyServiceImpl instance;	
	
	private BuddyServiceImpl(AssetManager assertManager, String xmlFileName, String parentTag){
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(assertManager.open(xmlFileName));
			buddyXmlAdapter = new BuddyXmlAdapter(doc, parentTag);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}
	
	@Override
	public List<Buddy> getBuddies() {
		return buddyXmlAdapter.getBuddies();
	}

	@Override
	public List<Buddy> searchBuddies(String searchTerm, List<Buddy> items) {
		int textLength = searchTerm.length();
		List<Buddy> newSearchedDashBoard = new ArrayList<Buddy>();

		for (Buddy buddy : items) {

			if (textLength <= buddy.getName().length()) {
				if (searchTerm.equalsIgnoreCase(buddy.getName()
						.substring(0, textLength))) {
					newSearchedDashBoard.add(buddy);
				}
			}
		}
		return newSearchedDashBoard;
	}
	
	public static BuddyServiceImpl getInstance(AssetManager aM, String xmlFileName, String parentTag){
		if(instance == null){
			instance = new BuddyServiceImpl(aM, xmlFileName, parentTag);
		}
		return instance;
	}

}
