package org.awi.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BuddySearchTag implements Parcelable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String searchTagName;
	private String buddyId;
	
	public BuddySearchTag(){}

	public BuddySearchTag(int id, String searchTagName, String buddyId){
		this.id = id;
		this.searchTagName = searchTagName;
		this.buddyId = buddyId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSearchTagName() {
		return searchTagName;
	}

	public void setSearchTagName(String searchTagName) {
		this.searchTagName = searchTagName;
	}

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(searchTagName);
		dest.writeInt(id);
		dest.writeString(buddyId);
	}
	
	public static final Parcelable.Creator<BuddySearchTag> CREATOR = new Creator<BuddySearchTag>() {

		@Override
		public BuddySearchTag createFromParcel(Parcel in) {
			return new BuddySearchTag(in);
		}

		@Override
		public BuddySearchTag[] newArray(int size) {
			return new BuddySearchTag[size];
		}
	};
	
	public BuddySearchTag(Parcel in){
		this.searchTagName = in.readString();
		this.id = in.readInt();
		this.buddyId = in.readString();
	}
}
