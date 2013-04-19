package org.awi.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BuddyLocation implements Parcelable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String locationName;
	private String buddyId;
	
	public BuddyLocation(){}
	
	public BuddyLocation(int id, String locationName, String buddyId){
		this.id = id;
		this.locationName = locationName;
		this.buddyId = buddyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
		dest.writeString(locationName);
		dest.writeInt(id);
		dest.writeString(buddyId);
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
		this.id = in.readInt();
		this.buddyId = in.readString();
	}
}
