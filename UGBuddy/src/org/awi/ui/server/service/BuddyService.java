package org.awi.ui.server.service;

import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.BuddyLocation;
import org.awi.ui.model.BuddySearchTag;


public interface BuddyService {
	
	public List<Buddy> getAllBuddiesByDashboardCategoryId(int dashboardCategoryId);
	public void addBuddy(Buddy buddy);
	public int updateBuddy(Buddy buddy);
	public void deleteBuddy(Buddy buddy);
	public String getUpdateDate();
	public void setUpdateDate(String updateDate);
	public Buddy getBuddyByName(String buddyName);
	public List<BuddyLocation> getBuddyLocationsByBuddyId(String buddyId);
	public void addBuddyLocation(BuddyLocation buddyLocation);
	public void deleteBuddyLocation(BuddyLocation buddyLocation);
	public int updateBuddyLocation(BuddyLocation buddyLocation);
	public List<BuddySearchTag> getBuddySearchTagByBuddyId(String buddyId);
	public void addBuddySearchTag(BuddySearchTag buddySearchTag);
	public void deleteBuddySearchTag(BuddySearchTag buddySearchTag);
	public int updateBuddySearchTag(BuddySearchTag buddySearchTag);
	public String getBuddyLocationUpdateDate();
	public String getBuddySearchTagUpdateDate();
	public void setBuddyLocationUpdateDate(String updateDate);
	public void setBuddySearchTagUpdateDate(String updateDate);
	public List<BuddyLocation> getAllBuddyLocations();
	public Buddy getBuddyById(int id);
	public BuddyLocation getBuddyLocationById(int id);
	public BuddySearchTag getBuddySearchTagById(int id);
}
