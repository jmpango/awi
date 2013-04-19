package org.awi.ui.server.service;

import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.DashBoard;

public interface SearchService {
	public List<Buddy> listingSearch(String query, List<Buddy> items);
	public List<DashBoard> dashboardSearch(String query, List<DashBoard> items);
}
