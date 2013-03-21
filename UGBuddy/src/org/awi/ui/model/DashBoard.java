package org.awi.ui.model;

public class DashBoard implements Comparable<DashBoard>{
	private String id;
	private String name;
	private String tagLine;

	public DashBoard() {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(DashBoard dashboard) {
		if (this.getName() == null || dashboard.getName() == null)
			return 0;
		return this.name.compareToIgnoreCase(dashboard.name);
	}

}
