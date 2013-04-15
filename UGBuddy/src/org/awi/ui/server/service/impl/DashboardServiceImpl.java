package org.awi.ui.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.awi.ui.model.DashBoard;
import org.awi.ui.server.dao.DashboardDAO;
import org.awi.ui.server.service.DashboardService;

import android.content.Context;

public class DashboardServiceImpl implements DashboardService {
	private DashboardDAO dashboardDAO;

	public DashboardServiceImpl(Context context) {
		this.dashboardDAO = new DashboardDAO(context);
	}

	@Override
	public DashBoard getDashboardById(int id) {
		return dashboardDAO.getDashboardById(id);
	}

	@Override
	public List<DashBoard> getAllDashboards() {
		return dashboardDAO.getAllDashboards();
	}

	@Override
	public int getDashboardsCount() {
		return dashboardDAO.getDashboardsCount();
	}

	@Override
	public void addDashboard(DashBoard dashboard) {
		dashboardDAO.addDashboard(dashboard);
	}

	@Override
	public int updateDashboard(DashBoard dashboard) {
		return dashboardDAO.updateDashboard(dashboard);
	}

	@Override
	public void deleteDashboard(DashBoard dashboard) {
		dashboardDAO.deleteDashboard(dashboard);
	}

	@Override
	public List<DashBoard> dashboardSearch(String query,
			List<DashBoard> dashboards) {
		int textLength = query.length();
		List<DashBoard> newSearchedDashBoard = new ArrayList<DashBoard>();

		if (dashboards != null) {
			for (DashBoard dashboard : dashboards) {
				if (textLength <= dashboard.getName().length()) {
					if (query.equalsIgnoreCase(dashboard.getName().substring(0,
							textLength))) {
						newSearchedDashBoard.add(dashboard);
					}
				}
			}
		}
		Collections.sort(newSearchedDashBoard);
		return newSearchedDashBoard;
	}

	@Override
	public String getUpdateDate() {
		return dashboardDAO.getUpdateDate();
	}

	@Override
	public void setUpdateDate(String newUpdateDate) {
		dashboardDAO.setUpdateDate(newUpdateDate);
	}
}
