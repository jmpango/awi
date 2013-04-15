package org.awi.ui.server.service;

import java.util.List;

import org.awi.ui.model.DashboardCategory;

public interface DashboardCategoryService {
	
	public DashboardCategory getDashboardCategoryById(int id);
	public List<DashboardCategory> getAllDashboardCategoryByDashboardId(int dashboardId);
	public int getDashboardCategoryCount();
	public void addDashboardCategory(DashboardCategory dashboardCategory);
	public int updateDashboardCategory(DashboardCategory dashboardCategory);
	public void deleteDashboardCategory(DashboardCategory dashboardCategory);
	public String getUpdateDate();
	public void setUpdateDate(String newUpdateDate);
	public DashboardCategory getDashboardCategoryByName(String cateoryName);
}
