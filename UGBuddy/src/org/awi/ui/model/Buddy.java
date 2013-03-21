package org.awi.ui.model;

import java.io.Serializable;
import java.util.List;

public class Buddy implements Serializable, Comparable<Buddy> {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String tagLine;
	private String email;
	private String telphoneNos;
	private String url;
	private String address;
	private List<Location> locations;
	private List<SearchTag> searchTags;

	public Buddy() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelphoneNos() {
		return telphoneNos;
	}

	public void setTelphoneNos(String telphoneNos) {
		this.telphoneNos = telphoneNos;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<SearchTag> getSearchTags() {
		return searchTags;
	}

	public void setSearchTags(List<SearchTag> searchTags) {
		this.searchTags = searchTags;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int compareTo(Buddy buddy) {
		if (this.getName() == null || buddy.getName() == null)
			return 0;
		return this.name.compareToIgnoreCase(buddy.name);
	}
}
