package org.awi.buddy.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.buddy.model.DashBoard;
import org.awi.buddy.server.adapter.DashboardXmlAdapter;
import org.awi.buddy.server.service.DashboardService;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;

public class DashboardServiceImpl implements DashboardService {

	private DashboardXmlAdapter dashBoardXmlAdapter;
	public static DashboardServiceImpl instance;
	
	private DashboardServiceImpl(AssetManager assetManager){
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(assetManager.open("dashboard.xml"));
			dashBoardXmlAdapter = new DashboardXmlAdapter(doc);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		
	}
	
	public static DashboardServiceImpl getInstance(AssetManager aM){
		if(instance == null){
			instance = new DashboardServiceImpl(aM);
		}
		return instance;
		
	}
	
	@Override
	public List<DashBoard> getDashBoardData() {
		return dashBoardXmlAdapter.getItems();
	}

	@Override
	public List<DashBoard> searchDashBoardData(String searchString, List<DashBoard> items) {
		int textLength = searchString.length();
		List<DashBoard> newSearchedDashBoard = new ArrayList<DashBoard>();

		for (DashBoard dashBoard : items) {

			if (textLength <= dashBoard.getName().length()) {
				if (searchString.equalsIgnoreCase(dashBoard.getName()
						.substring(0, textLength))) {
					newSearchedDashBoard.add(dashBoard);
				}
			}
		}
		return newSearchedDashBoard;
	}

}
