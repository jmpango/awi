package org.awi.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable{
	private static final long serialVersionUID = 1L;
	
	private String locationName;
	
	public Location(){}

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
	
	public static final Parcelable.Creator<Location> CREATOR = new Creator<Location>() {

		@Override
		public Location createFromParcel(Parcel in) {
			return new Location(in);
		}

		@Override
		public Location[] newArray(int size) {
			return new Location[size];
		}
	};
	
	public Location(Parcel in){
		this.locationName = in.readString();
	}
}
