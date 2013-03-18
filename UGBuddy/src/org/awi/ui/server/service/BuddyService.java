package org.awi.ui.server.service;

import java.util.List;

import org.awi.ui.model.Buddy;

import android.content.res.AssetManager;

public interface BuddyService {

	List<Buddy> getBuddies(AssetManager assertManager, String xmlFileName, String parentTag);

	List<Buddy> searchBuddies(String searchTerm, List<Buddy> items);
}
