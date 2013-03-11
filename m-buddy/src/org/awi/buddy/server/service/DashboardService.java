package org.awi.buddy.server.service;

import java.util.List;

import org.awi.buddy.model.DashBoard;

public interface DashboardService {

	List<DashBoard> getDashBoardData();
	List<DashBoard> searchDashBoardData(String searchTerm, List<DashBoard> items);
}
