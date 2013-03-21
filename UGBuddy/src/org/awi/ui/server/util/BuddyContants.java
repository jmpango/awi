package org.awi.ui.server.util;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.SplashScreen.BuddyNamesXmlAdapter;
import org.awi.ui.model.Buddy;
import org.awi.ui.model.Globals;
import org.awi.ui.server.service.BuddyService;

import android.content.res.AssetManager;

public final class BuddyContants {
	
	private BuddyContants(){}
	
	public static final String LISTING = "listing";
	public static final String PAGE_NAME = "pageName";
	public static final String DEFAULT_XML_EXTENTION = "Listing.xml";
	public static final String DEFAULT_BUDDY = "buddy";
	public static final String BUDDY_NAMES_XML = "appNames.xml";
	public static final String NO_RESULT_FOUND = "No search Found";
	public static final String DEFAULT_APP_EMAIL = "info@ugbuddy.ug";
	
	public static final String CONVERT_FIRST_LETTER_CAPS(String letter){
		return letter.substring(0,1).toUpperCase()+ letter.substring(1, letter.length());
	}
	
	public static final String REMOVE_WHITESPACES(String word){
		return word.replaceAll("\\s+", "");
	}
	
	public static final void LOAD_APP_DATA(AssetManager assetManager, BuddyService buddyService) {
		BuddyNamesXmlAdapter instance = BuddyNamesXmlAdapter
				.getInstance(assetManager);
		List<String> names = instance.getNames();
		Globals globals = Globals.getInstance();

		List<Buddy> buddies = new ArrayList<Buddy>();

		for (String name : names) {
			buddies = buddyService.getBuddies(assetManager, "buddies/"
					+ name + DEFAULT_XML_EXTENTION, name);
			globals.addGlobalData(name, buddies);
		}
	}
}
