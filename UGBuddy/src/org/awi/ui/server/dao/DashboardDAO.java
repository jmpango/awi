package org.awi.ui.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.DashBoard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DashboardDAO extends BaseDAO {
	private static final String DASHBOARD_TABLE = "dashboard";

	public DashboardDAO(Context context) {
		super(context);
	}

	public DashBoard getDashboardById(int id) {
		SQLiteDatabase db = this.dbOpen();

		Cursor cursor = db.query(DASHBOARD_TABLE, new String[] { "id", "dname",
				"tagline" }, "id = ?", new String[] { String.valueOf(id) },
				null, null, null);
		DashBoard dashboard = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				dashboard = new DashBoard(
						Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2));
			}
		}

		cursor.close();
		this.dbClose(db);
		return dashboard;
	}

	public List<DashBoard> getAllDashboards() {
		List<DashBoard> dashboardList = new ArrayList<DashBoard>();

		String sql = "SELECT * FROM " + DASHBOARD_TABLE;
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			do {
				DashBoard dashboard = new DashBoard();
				dashboard.setId(Integer.parseInt(cursor.getString(0)));
				dashboard.setName(cursor.getString(1));
				dashboard.setTagLine(cursor.getString(2));

				dashboardList.add(dashboard);
			} while (cursor.moveToNext());
		}

		cursor.close();
		this.dbClose(db);

		return dashboardList;
	}

	public int getDashboardsCount() {
		String sql = "SELECT * FROM " + DASHBOARD_TABLE;
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql, null);

		int count = cursor.getCount();
		cursor.close();
		this.dbClose(db);

		return count;
	}

	public void addDashboard(DashBoard dashboard) {
		ContentValues values = new ContentValues();
		values.put("id", dashboard.getId());
		values.put("dname", dashboard.getName());
		values.put("tagline", dashboard.getTagLine());

		SQLiteDatabase db = this.dbOpen();
		db.insert(DASHBOARD_TABLE, null, values);

		values.clear();
		values = null;

		this.dbClose(db);
	}

	public int updateDashboard(DashBoard dashboard) {
		ContentValues values = new ContentValues();
		values.put("dname", dashboard.getName());
		values.put("tagline", dashboard.getTagLine());

		SQLiteDatabase db = this.dbOpen();
		int isUpadated = db.update(DASHBOARD_TABLE, values, "id = ?",
				new String[] { String.valueOf(dashboard.getId()) });
		values.clear();
		values = null;

		this.dbClose(db);
		return isUpadated;
	}

	public void deleteDashboard(DashBoard dashboard) {
		SQLiteDatabase db = this.dbOpen();
		db.delete(DASHBOARD_TABLE, "id = ?",
				new String[] { String.valueOf(dashboard.getId()) });
		this.dbClose(db);
	}

	public String getUpdateDate() {
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.query(SYS_UPDATE_TABLE, new String[] { "id", "name",
				"last_update_date" }, "name = ?", new String[] { "dashboard" },
				null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		String lastDate = cursor.getString(2);

		cursor.close();
		this.dbClose(db);
		return lastDate;
	}

	public void setUpdateDate(String newUpdateDate) {
		ContentValues values = new ContentValues();
		values.put("last_update_date", newUpdateDate);

		SQLiteDatabase db = this.dbOpen();
		db.update(SYS_UPDATE_TABLE, values, "name = ?",
				new String[] { "dashboard" });
		values.clear();
		values = null;

		this.dbClose(db);
	}

}
