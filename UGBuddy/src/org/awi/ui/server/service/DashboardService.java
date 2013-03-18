package org.awi.ui.server.service;

import java.util.List;

import org.awi.ui.model.DashBoard;

public interface DashboardService {

	List<DashBoard> getDashBoardData();
	List<DashBoard> searchDashBoardData(String searchTerm, List<DashBoard> items);
}
