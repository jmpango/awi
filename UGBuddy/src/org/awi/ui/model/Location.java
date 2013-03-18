package org.awi.ui.model;

import java.io.Serializable;

public class Location implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public Location(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
