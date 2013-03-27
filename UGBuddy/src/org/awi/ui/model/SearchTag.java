package org.awi.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchTag implements Parcelable{
	
	private static final long serialVersionUID = 1L;
	
	private String searchTagName;
	
	public SearchTag(){}

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
	
	public static final Parcelable.Creator<SearchTag> CREATOR = new Creator<SearchTag>() {

		@Override
		public SearchTag createFromParcel(Parcel in) {
			return new SearchTag(in);
		}

		@Override
		public SearchTag[] newArray(int size) {
			return new SearchTag[size];
		}
	};
	
	public SearchTag(Parcel in){
		this.searchTagName = in.readString();
	}
}
