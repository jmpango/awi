package org.awi.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BuddyLocation implements Parcelable{
	private static final long serialVersionUID = 1L;
	
	private String locationName;
	
	public BuddyLocation(){}

	public String getName() {
		return locationName;
	}

	public void setName(String name) {
		this.locationName = name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(locationName);
	}
	
	public static final Parcelable.Creator<BuddyLocation> CREATOR = new Creator<BuddyLocation>() {

		@Override
		public BuddyLocation createFromParcel(Parcel in) {
			return new BuddyLocation(in);
		}

		@Override
		public BuddyLocation[] newArray(int size) {
			return new BuddyLocation[size];
		}
	};
	
	public BuddyLocation(Parcel in){
		this.locationName = in.readString();
	}
}
