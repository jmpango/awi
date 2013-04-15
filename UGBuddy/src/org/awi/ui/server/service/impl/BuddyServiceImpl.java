package org.awi.ui.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.server.service.BuddyService;

import android.content.res.AssetManager;

public class BuddyServiceImpl implements BuddyService{
	
	public static BuddyServiceImpl instance;	
	
	private BuddyServiceImpl(){
		/*try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			//docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}*/
	}
	
	@Override
	public List<Buddy> getBuddies(AssetManager assertManager, String xmlFileName, String parentTag) {
		return null;
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
		
		Collections.sort(newSearchedDashBoard);
		return newSearchedDashBoard;
	}
	
	public static BuddyServiceImpl getInstance(){
		if(instance == null){
			instance = new BuddyServiceImpl();
		}
		return instance;
	}

}
