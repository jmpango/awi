package org.awi.ui.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Buddy implements Parcelable, Comparable<Buddy> {

	private static final long serialVersionUID = 1L;
	
	/** private OBJECT obj; */
	
	private String id;
	private String name;
	private String tagLine;
	private String email;
	private String telphoneNos;
	private String url;
	private String address;
	private List<BuddyLocation> locations;
	private List<BuddySearchTag> searchTags;

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

	public List<BuddyLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<BuddyLocation> locations) {
		this.locations = locations;
	}

	public List<BuddySearchTag> getSearchTags() {
		return searchTags;
	}

	public void setSearchTags(List<BuddySearchTag> searchTags) {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(tagLine);
		dest.writeString(email);
		dest.writeString(telphoneNos);
		dest.writeString(url);
		dest.writeString(address);
		dest.writeTypedList(locations);
		dest.writeTypedList(searchTags);
		
		/** dest.writeParcelable(obj, flags); */
	}
	
	public static final Parcelable.Creator<Buddy> CREATOR = new Creator<Buddy>() {
		
		@Override
		public Buddy[] newArray(int size) {
			return new Buddy[size];
		}
		
		@Override
		public Buddy createFromParcel(Parcel in) {
			return new Buddy(in);
		}
	};
	
	public Buddy(Parcel in){
		this.id = in.readString();
		this.name = in.readString();
		this.tagLine = in.readString();
		this.email = in.readString();
		this.telphoneNos = in.readString();
		this.url = in.readString();
		this.address = in.readString();
		
		if(locations == null)
			locations = new ArrayList<BuddyLocation>();
		in.readTypedList(locations, BuddyLocation.CREATOR);
		
		if(searchTags == null)
			searchTags = new ArrayList<BuddySearchTag>();
		in.readTypedList(searchTags, BuddySearchTag.CREATOR);
		
		/** obj = in.readParcelable(OBJECT.class.getClassLoader()); */
	}
}
