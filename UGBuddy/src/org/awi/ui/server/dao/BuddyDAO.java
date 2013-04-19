package org.awi.ui.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.BuddyLocation;
import org.awi.ui.model.BuddySearchTag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BuddyDAO extends BaseDAO {
	private static final String BUDDY_TABLE = "buddy";
	private static final String BUDDY_LOCATION_TABLE = "buddy_location";
	private static final String BUDDY_SEARCHTAG_TABLE = "buddy_search_tag";

	public BuddyDAO(Context context) {
		super(context);
	}

	public List<Buddy> getAllBuddiesByDashboardCategoryId(
			int dashboardCategoryId) {
		List<Buddy> buddyList = new ArrayList<Buddy>();

		String sql = "SELECT * FROM " + BUDDY_TABLE
				+ " WHERE dashboard_category_id = ?";
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql,
				new String[] { String.valueOf(dashboardCategoryId) });

		if (cursor.moveToFirst()) {
			do {
				Buddy buddy = new Buddy();
				buddy.setId(Integer.parseInt(cursor.getString(0)));
				buddy.setName(cursor.getString(1));
				buddy.setTagLine(cursor.getString(2));
				buddy.setAddress(cursor.getString(3));
				buddy.setTelphoneNos(cursor.getString(4));
				buddy.setEmail(cursor.getString(5));
				buddy.setFax(cursor.getString(6));
				buddy.setUrl(cursor.getString(7));
				buddy.setDashboardCategoryId(cursor.getString(8));

				buddyList.add(buddy);
			} while (cursor.moveToNext());
		}

		cursor.close();
		this.dbClose(db);

		return buddyList;
	}

	public void addBuddy(Buddy buddy) {
		ContentValues values = new ContentValues();
		values.put("id", buddy.getId());
		values.put("name", buddy.getName());
		values.put("tagline", buddy.getTagLine());
		values.put("address", buddy.getAddress());
		values.put("telphone", buddy.getTelphoneNos());
		values.put("email", buddy.getEmail());
		values.put("fax", buddy.getFax());
		values.put("url", buddy.getUrl());
		values.put("dashboard_category_id", buddy.getDashboardCategoryId());

		SQLiteDatabase db = this.dbOpen();
		db.insert(BUDDY_TABLE, null, values);

		values.clear();
		values = null;

		this.dbClose(db);

	}

	public int updateBuddy(Buddy buddy) {
		ContentValues values = new ContentValues();
		values.put("id", buddy.getId());
		values.put("name", buddy.getName());
		values.put("tagline", buddy.getTagLine());
		values.put("address", buddy.getAddress());
		values.put("telphone", buddy.getTelphoneNos());
		values.put("email", buddy.getEmail());
		values.put("fax", buddy.getFax());
		values.put("url", buddy.getUrl());
		values.put("dashboard_category_id", buddy.getDashboardCategoryId());

		SQLiteDatabase db = this.dbOpen();
		int isUpadated = db.update(BUDDY_TABLE, values, "id = ?",
				new String[] { String.valueOf(buddy.getId()) });
		values.clear();
		values = null;

		this.dbClose(db);
		return isUpadated;
	}

	public void deleteBuddy(Buddy buddy) {
		SQLiteDatabase db = this.dbOpen();
		db.delete(BUDDY_TABLE, "id = ?",
				new String[] { String.valueOf(buddy.getId()) });
		this.dbClose(db);

	}

	public String getUpdateDate() {
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.query(SYS_UPDATE_TABLE, new String[] { "id", "name",
				"last_update_date" }, "name = ?", new String[] { "buddy" },
				null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		String lastDate = cursor.getString(2);

		cursor.close();
		this.dbClose(db);
		return lastDate;
	}

	public void setUpdateDate(String updateDate) {
		ContentValues values = new ContentValues();
		values.put("last_update_date", updateDate);

		SQLiteDatabase db = this.dbOpen();
		db.update(SYS_UPDATE_TABLE, values, "name = ?",
				new String[] { "buddy" });
		values.clear();
		values = null;

		this.dbClose(db);

	}

	public Buddy getBuddyByName(String buddyName) {
		SQLiteDatabase db = this.dbOpen();

		Cursor cursor = db.query(BUDDY_TABLE, new String[] { "id", "name",
				"tagline", "address", "telphone", "email", "fax", "url",
				"dashboard_category_id" }, "name = ?",
				new String[] { String.valueOf(buddyName) }, null, null, null);
		Buddy buddy = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				buddy = new Buddy(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(5), cursor.getString(4),
						cursor.getString(7), cursor.getString(6),
						cursor.getString(3), cursor.getString(7));
			}
		}

		cursor.close();
		this.dbClose(db);

		return buddy;
	}

	public List<BuddyLocation> getBuddyLocationsByBuddyId(String buddyId) {
		List<BuddyLocation> buddyLocationList = new ArrayList<BuddyLocation>();

		String sql = "SELECT * FROM " + BUDDY_LOCATION_TABLE
				+ " WHERE duddy_id = ?";
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql,
				new String[] { String.valueOf(buddyId) });

		if (cursor.moveToFirst()) {
			do {
				BuddyLocation buddyLocation = new BuddyLocation();
				buddyLocation.setId(Integer.parseInt(cursor.getString(0)));
				buddyLocation.setLocationName(cursor.getString(1));
				buddyLocation.setBuddyId(cursor.getString(2));

				buddyLocationList.add(buddyLocation);
			} while (cursor.moveToNext());
		}

		cursor.close();
		this.dbClose(db);

		return buddyLocationList;
	}

	public void addBuddyLocation(BuddyLocation buddyLocation) {
		ContentValues values = new ContentValues();
		values.put("id", buddyLocation.getId());
		values.put("location_name", buddyLocation.getLocationName());
		values.put("buddy_id", buddyLocation.getBuddyId());

		SQLiteDatabase db = this.dbOpen();
		db.insert(BUDDY_LOCATION_TABLE, null, values);

		values.clear();
		values = null;

		this.dbClose(db);
	}

	public void deleteBuddyLocation(BuddyLocation buddyLocation) {
		SQLiteDatabase db = this.dbOpen();
		db.delete(BUDDY_LOCATION_TABLE, "id = ?",
				new String[] { String.valueOf(buddyLocation.getId()) });
		this.dbClose(db);
	}

	public int updateBuddyLocation(BuddyLocation buddyLocation) {
		ContentValues values = new ContentValues();
		values.put("id", buddyLocation.getId());
		values.put("location_name", buddyLocation.getLocationName());
		values.put("buddy_id", buddyLocation.getBuddyId());

		SQLiteDatabase db = this.dbOpen();
		int isUpadated = db.update(BUDDY_LOCATION_TABLE, values, "id = ?",
				new String[] { String.valueOf(buddyLocation.getId()) });
		values.clear();
		values = null;

		this.dbClose(db);
		return isUpadated;
	}

	public List<BuddySearchTag> getBuddySearchTagByBuddyId(String buddyId) {
		List<BuddySearchTag> buddySearchTagList = new ArrayList<BuddySearchTag>();

		String sql = "SELECT * FROM " + BUDDY_SEARCHTAG_TABLE
				+ " WHERE duddy_id = ?";
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql,
				new String[] { String.valueOf(buddyId) });

		if (cursor.moveToFirst()) {
			do {
				BuddySearchTag buddySearchTag = new BuddySearchTag();
				buddySearchTag.setId(Integer.parseInt(cursor.getString(0)));
				buddySearchTag.setSearchTagName(cursor.getString(1));
				buddySearchTag.setBuddyId(cursor.getString(2));

				buddySearchTagList.add(buddySearchTag);
			} while (cursor.moveToNext());
		}

		cursor.close();
		this.dbClose(db);

		return buddySearchTagList;
	}

	public void addBuddySearchTag(BuddySearchTag buddySearchTag) {
		ContentValues values = new ContentValues();
		values.put("id", buddySearchTag.getId());
		values.put("searchtag", buddySearchTag.getSearchTagName());
		values.put("buddy_id", buddySearchTag.getBuddyId());

		SQLiteDatabase db = this.dbOpen();
		db.insert(BUDDY_SEARCHTAG_TABLE, null, values);

		values.clear();
		values = null;

		this.dbClose(db);
	}

	public void deleteBuddySearchTag(BuddySearchTag buddySearchTag) {
		SQLiteDatabase db = this.dbOpen();
		db.delete(BUDDY_SEARCHTAG_TABLE, "id = ?",
				new String[] { String.valueOf(buddySearchTag.getId()) });
		this.dbClose(db);
	}

	public int updateBuddySearchTag(BuddySearchTag buddySearchTag) {
		ContentValues values = new ContentValues();
		values.put("id", buddySearchTag.getId());
		values.put("searchtag", buddySearchTag.getSearchTagName());
		values.put("buddy_id", buddySearchTag.getBuddyId());

		SQLiteDatabase db = this.dbOpen();
		int isUpadated = db.update(BUDDY_SEARCHTAG_TABLE, values, "id = ?",
				new String[] { String.valueOf(buddySearchTag.getId()) });
		values.clear();
		values = null;

		this.dbClose(db);
		return isUpadated;
	}

	public String getBuddyLocationUpdateDate() {
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.query(SYS_UPDATE_TABLE, new String[] { "id", "name",
				"last_update_date" }, "name = ?",
				new String[] { "buddylocation" }, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		String lastDate = cursor.getString(2);

		cursor.close();
		this.dbClose(db);
		return lastDate;
	}

	public String getBuddySearchTagUpdateDate() {
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.query(SYS_UPDATE_TABLE, new String[] { "id", "name",
				"last_update_date" }, "name = ?",
				new String[] { "buddysearchtag" }, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		String lastDate = cursor.getString(2);

		cursor.close();
		this.dbClose(db);
		return lastDate;
	}

	public void setBuddyLocationUpdateDate(String updateDate) {
		ContentValues values = new ContentValues();
		values.put("last_update_date", updateDate);

		SQLiteDatabase db = this.dbOpen();
		db.update(SYS_UPDATE_TABLE, values, "name = ?",
				new String[] { "buddylocation" });
		values.clear();
		values = null;

		this.dbClose(db);
	}

	public void setBuddySearchTagUpdateDate(String updateDate) {
		ContentValues values = new ContentValues();
		values.put("last_update_date", updateDate);

		SQLiteDatabase db = this.dbOpen();
		db.update(SYS_UPDATE_TABLE, values, "name = ?",
				new String[] { "buddysearchtag" });
		values.clear();
		values = null;

		this.dbClose(db);
	}

	public List<BuddyLocation> getAllBuddyLocations() {
		List<BuddyLocation> buddyLocationList = new ArrayList<BuddyLocation>();

		String sql = "SELECT * FROM " + BUDDY_LOCATION_TABLE;
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			do {
				BuddyLocation buddyLocation = new BuddyLocation();
				buddyLocation.setId(Integer.parseInt(cursor.getString(0)));
				buddyLocation.setLocationName(cursor.getString(1));
				buddyLocation.setBuddyId(cursor.getString(2));

				buddyLocationList.add(buddyLocation);
			} while (cursor.moveToNext());
		}

		cursor.close();
		this.dbClose(db);

		return buddyLocationList;
	}

	public Buddy getBuddyById(int id) {
		SQLiteDatabase db = this.dbOpen();

		Cursor cursor = db.query(BUDDY_TABLE, new String[] { "id", "name",
				"tagline", "address", "telphone", "email", "fax", "url",
				"dashboard_category_id" }, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		Buddy buddy = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				buddy = new Buddy(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(5), cursor.getString(4),
						cursor.getString(7), cursor.getString(6),
						cursor.getString(3), cursor.getString(7));
			}
		}

		cursor.close();
		this.dbClose(db);
		return buddy;
	}

	public BuddyLocation getBuddyLocationById(int id) {
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.query(BUDDY_LOCATION_TABLE, new String[] { "id",
				"location_name", "buddy_id" }, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		BuddyLocation buddyLocation = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				buddyLocation = new BuddyLocation(Integer.parseInt(cursor
						.getString(0)), cursor.getString(1),
						cursor.getString(2));
			}
		}

		cursor.close();
		this.dbClose(db);
		return buddyLocation;
	}

	public BuddySearchTag getBuddySearchTagById(int id) {
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.query(BUDDY_SEARCHTAG_TABLE, new String[] { "id",
				"searchtag", "buddy_id" }, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		BuddySearchTag buddySearchTag = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				buddySearchTag = new BuddySearchTag(Integer.parseInt(cursor
						.getString(0)), cursor.getString(1),
						cursor.getString(2));
			}
		}
		cursor.close();
		this.dbClose(db);
		return buddySearchTag;
	}

}
