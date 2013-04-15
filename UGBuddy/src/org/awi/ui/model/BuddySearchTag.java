package org.awi.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BuddySearchTag implements Parcelable{
	
	private static final long serialVersionUID = 1L;
	
	private String searchTagName;
	
	public BuddySearchTag(){}

	public String getName() {
		return searchTagName;
	}

	public void setName(String name) {
		this.searchTagName = name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(searchTagName);
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
	}
}
