package org.awi.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Buddy implements Parcelable, Comparable<Buddy> {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String tagLine;
	private String email;
	private String telphoneNos;
	private String url;
	private String fax;
	private String address;
	private String dashboardCategoryId;

	public Buddy() {
	}

	public Buddy(int id, String name, String tagline, String email,
			String telphoneNos, String url, String fax, String address,
			String dashboardCategoryId) {
		this.id = id;
		this.name = name;
		this.tagLine = tagline;
		this.email = email;
		this.telphoneNos = telphoneNos;
		this.url = url;
		this.fax = fax;
		this.address = address;
		this.dashboardCategoryId = dashboardCategoryId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDashboardCategoryId() {
		return dashboardCategoryId;
	}

	public void setDashboardCategoryId(String dashboardCategoryId) {
		this.dashboardCategoryId = dashboardCategoryId;
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
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(tagLine);
		dest.writeString(email);
		dest.writeString(telphoneNos);
		dest.writeString(url);
		dest.writeString(fax);
		dest.writeString(address);
		dest.writeString(dashboardCategoryId);
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

	public Buddy(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.tagLine = in.readString();
		this.email = in.readString();
		this.telphoneNos = in.readString();
		this.url = in.readString();
		this.fax = in.readString();
		this.address = in.readString();
		this.dashboardCategoryId = in.readString();
	}
}
