package org.awi.ui.server.service;

import java.util.List;

import org.awi.ui.model.DashBoard;

public interface DashboardService {
	
	public DashBoard getDashboardById(int id);
	public List<DashBoard> getAllDashboards();
	public int getDashboardsCount();
	public void addDashboard(DashBoard dashboard);
	public int updateDashboard(DashBoard dashboard);
	public void deleteDashboard(DashBoard dashboard);
	public List<DashBoard> dashboardSearch(String query, List<DashBoard> dashboards);
	public String getUpdateDate();
	public void setUpdateDate(String newUpdateDate);
}
