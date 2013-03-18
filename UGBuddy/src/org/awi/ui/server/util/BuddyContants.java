package org.awi.ui.server.util;

public final class BuddyContants {
	
	private BuddyContants(){}
	
	public static final String LISTING = "listing";
	public static final String PAGE_NAME = "pageName";
	public static final String DEFAULT_XML_EXTENTION = "Listing.xml";
	public static final String DEFAULT_BUDDY = "buddy";
	public static final String BUDDY_NAMES_XML = "buddyNames.xml";
	public static final String NO_RESULT_FOUND = "No search Found";
	
	public static final String CONVERT_FIRST_LETTER_CAPS(String letter){
		return letter.substring(0,1).toUpperCase()+ letter.substring(1, letter.length());
	}
}
