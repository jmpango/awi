package org.awi.buddy.server.service;

import java.util.List;

import org.awi.buddy.model.Buddy;

public interface BuddyService {

	List<Buddy> getBuddies();

	List<Buddy> searchBuddies(String searchTerm, List<Buddy> items);
}
