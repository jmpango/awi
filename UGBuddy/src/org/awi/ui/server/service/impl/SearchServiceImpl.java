package org.awi.ui.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.DashBoard;
import org.awi.ui.server.service.SearchService;

public class SearchServiceImpl implements SearchService {

	@Override
	public List<Buddy> listingSearch(String query, List<Buddy> items) {
		int textLength = query.length();
		List<Buddy> newSearchedBuddy = new ArrayList<Buddy>();

		if (items != null) {
			for (Buddy buddy : items) {
				if (textLength <= buddy.getName().length()) {
					if (query.equalsIgnoreCase(buddy.getName().substring(0,
							textLength))) {
						newSearchedBuddy.add(buddy);
					}
				}
			}
		}
		Collections.sort(newSearchedBuddy);
		return newSearchedBuddy;
	}

	@Override
	public List<DashBoard> dashboardSearch(String query, List<DashBoard> items) {
		int textLength = query.length();
		List<DashBoard> newSearchedDashBoard = new ArrayList<DashBoard>();

		if (items != null) {
			for (DashBoard dashboard : items) {
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

}
