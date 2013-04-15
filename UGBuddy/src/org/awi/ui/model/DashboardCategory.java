package org.awi.ui.model;

public class DashboardCategory implements Comparable<DashboardCategory> {
	private int id;
	private String categoryName;
	private String dashboardId;
	
	public DashboardCategory(){}
	
	public DashboardCategory(int id, String categoryName, String dashboardId){
		this.id = id;
		this.categoryName = categoryName;
		this.dashboardId = dashboardId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	@Override
	public int compareTo(DashboardCategory dashboardCategory) {
		if (this.getCategoryName() == null || dashboardCategory.getCategoryName() == null)
			return 0;
		return this.categoryName.compareToIgnoreCase(dashboardCategory.categoryName);
	}
	
}
