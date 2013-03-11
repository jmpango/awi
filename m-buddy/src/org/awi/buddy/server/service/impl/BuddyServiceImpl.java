package org.awi.buddy.server.service.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.buddy.model.Buddy;
import org.awi.buddy.server.adapter.BuddyXmlAdapter;
import org.awi.buddy.server.adapter.DashboardXmlAdapter;
import org.awi.buddy.server.service.BuddyService;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;

public class BuddyServiceImpl implements BuddyService{
	
	private BuddyXmlAdapter buddyXmlAdapter;
	public static BuddyServiceImpl instance;	
	
	private BuddyServiceImpl(AssetManager assertManager){
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(assertManager.open("dashboard.xml"));
			//TODO make the xml reader flexiable.
			//buddyXmlAdapter = new BuddyXmlAdapter(doc);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
	}
	
	@Override
	public List<Buddy> getBuddies() {
		return null;
	}

	@Override
	public List<Buddy> searchBuddies(String searchTerm, List<Buddy> items) {
		return null;
	}
	
	public static BuddyServiceImpl getInstance(AssetManager aM){
		if(instance == null){
			instance = new BuddyServiceImpl(aM);
		}
		return instance;
	}

}
