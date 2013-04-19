package org.awi.ui.server.service;

import java.util.List;

import org.awi.ui.model.Usage;

public interface UsageService {
	public Usage getBuddyUsage(int buddyId);
	public void savePageHit(int buddyId);
	public void saveCallHit(int buddyId);
	public void saveUrlHit(int buddyId);
	public void saveEmailHit(int buddyId);
	public void clearUsage();
	public List<Usage> getAllUsage();
}
