package org.awi.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Globals {
	private static Globals instance;
	private HashMap<String, List<Buddy>> dataz;
	
	private Globals(){
		dataz = new HashMap<String, List<Buddy>>();
	}
	
	public static synchronized Globals getInstance(){
		if(instance == null)
			instance = new Globals();
		return instance;
	}

	public HashMap<String, List<Buddy>> getDataz() {
		return dataz;
	}

	public void setDataz(HashMap<String, List<Buddy>> dataz) {
		this.dataz = dataz;
	}
	
	public void addGlobalData(String identifier, List<Buddy> buddies) {
		if (buddies == null) {
		    return;
		}

		if (this.getDataz() == null) {
		    this.setDataz(new HashMap<String, List<Buddy>>());
		}

		this.getDataz().put(identifier, buddies);
	    }
	
	public List<Buddy> getBuddy(String identifier){
		if(this.dataz == null)
			return new ArrayList<Buddy>();
			
		return this.dataz.get(identifier);
	}
}
