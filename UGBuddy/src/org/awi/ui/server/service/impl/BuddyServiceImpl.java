package org.awi.ui.server.service.impl;

import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.BuddyLocation;
import org.awi.ui.model.BuddySearchTag;
import org.awi.ui.server.dao.BuddyDAO;
import org.awi.ui.server.service.BuddyService;

import android.content.Context;

public class BuddyServiceImpl implements BuddyService{
	private BuddyDAO buddyDAO;
	
	public BuddyServiceImpl(Context context){
		buddyDAO = new BuddyDAO(context);
	}
	
	@Override
	public List<Buddy> getAllBuddiesByDashboardCategoryId(
			int dashboardCategoryId) {
		return buddyDAO.getAllBuddiesByDashboardCategoryId(dashboardCategoryId);
	}

	@Override
	public void addBuddy(Buddy buddy) {
		buddyDAO.addBuddy(buddy);
	}

	@Override
	public int updateBuddy(Buddy buddy) {
		return buddyDAO.updateBuddy(buddy);
	}

	@Override
	public void deleteBuddy(Buddy buddy) {
		buddyDAO.deleteBuddy(buddy);
	}

	@Override
	public String getUpdateDate() {
		return buddyDAO.getUpdateDate();
	}

	@Override
	public void setUpdateDate(String updateDate) {
		buddyDAO.setUpdateDate(updateDate);
	}

	@Override
	public Buddy getBuddyByName(String buddyName) {
		return buddyDAO.getBuddyByName(buddyName);
	}

	@Override
	public List<BuddyLocation> getBuddyLocationsByBuddyId(String buddyId) {
		return buddyDAO.getBuddyLocationsByBuddyId(buddyId);
	}

	@Override
	public void addBuddyLocation(BuddyLocation buddyLocation) {
		buddyDAO.addBuddyLocation(buddyLocation);
	}

	@Override
	public void deleteBuddyLocation(BuddyLocation buddyLocation) {
		buddyDAO.deleteBuddyLocation(buddyLocation);
	}

	@Override
	public int updateBuddyLocation(BuddyLocation buddyLocation) {
		return buddyDAO.updateBuddyLocation(buddyLocation);
	}

	@Override
	public List<BuddySearchTag> getBuddySearchTagByBuddyId(String buddyId) {
		return buddyDAO.getBuddySearchTagByBuddyId(buddyId);
	}

	@Override
	public void addBuddySearchTag(BuddySearchTag buddySearchTag) {
		buddyDAO.addBuddySearchTag(buddySearchTag);
	}

	@Override
	public void deleteBuddySearchTag(BuddySearchTag buddySearchTag) {
		buddyDAO.deleteBuddySearchTag(buddySearchTag);
	}

	@Override
	public int updateBuddySearchTag(BuddySearchTag buddySearchTag) {
		return buddyDAO.updateBuddySearchTag(buddySearchTag);
	}

	@Override
	public String getBuddyLocationUpdateDate() {
		return buddyDAO.getBuddyLocationUpdateDate();
	}

	@Override
	public String getBuddySearchTagUpdateDate() {
		return buddyDAO.getBuddySearchTagUpdateDate();
	}

	@Override
	public void setBuddyLocationUpdateDate(String updateDate) {
		buddyDAO.setBuddyLocationUpdateDate(updateDate);
	}

	@Override
	public void setBuddySearchTagUpdateDate(String updateDate) {
		buddyDAO.setBuddySearchTagUpdateDate(updateDate);
	}

	@Override
	public List<BuddyLocation> getAllBuddyLocations() {
		return buddyDAO.getAllBuddyLocations();
	}

	@Override
	public Buddy getBuddyById(int id) {
		return buddyDAO.getBuddyById(id);
	}

	@Override
	public BuddyLocation getBuddyLocationById(int id) {
		return buddyDAO.getBuddyLocationById(id);
	}

	@Override
	public BuddySearchTag getBuddySearchTagById(int id) {
		return buddyDAO.getBuddySearchTagById(id);
	}
}
