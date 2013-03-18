package org.awi.ui.model;

import java.io.Serializable;

public class SearchTag implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public SearchTag(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
