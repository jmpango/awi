package org.awi.ui.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.DashboardCategory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DashboardCategoryDAO extends BaseDAO {
	private static final String DASHBOARD_CATEGORY_TABLE = "dashboardcategory";
	private static final String SYS_UPDATE_TABLE = "sys_update";

	public DashboardCategoryDAO(Context context) {
		super(context);
	}

	public DashboardCategory getDashboardCategoryById(int id) {
		SQLiteDatabase db = this.dbOpen();

		Cursor cursor = db.query(DASHBOARD_CATEGORY_TABLE, new String[] { "id",
				"cname", "dashboard_id" }, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null);
		DashboardCategory dashboardCategory = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				dashboardCategory = new DashboardCategory(
						Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2));
			}
		}

		cursor.close();
		this.dbClose(db);
		return dashboardCategory;
	}

	public List<DashboardCategory> getAllDashboardCategoriesByDashboardId(
			int dashBoardId) {
		List<DashboardCategory> dashboardCategoryList = new ArrayList<DashboardCategory>();

		String sql = "SELECT * FROM " + DASHBOARD_CATEGORY_TABLE
				+ " WHERE dashboard_id = ?";
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(dashBoardId)});

		if (cursor.moveToFirst()) {
			do {
				DashboardCategory dashboardCategory = new DashboardCategory();
				dashboardCategory.setId(Integer.parseInt(cursor.getString(0)));
				dashboardCategory.setCategoryName(cursor.getString(1));
				dashboardCategory.setDashboardId(cursor.getString(2));

				dashboardCategoryList.add(dashboardCategory);
			} while (cursor.moveToNext());
		}

		cursor.close();
		this.dbClose(db);

		return dashboardCategoryList;
	}

	public int getDashboardCategoryCount() {
		String sql = "SELECT * FROM " + DASHBOARD_CATEGORY_TABLE;
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.rawQuery(sql, null);

		int count = cursor.getCount();
		cursor.close();
		this.dbClose(db);

		return count;
	}

	public void addDashboardCategory(DashboardCategory dashboardCategory) {
		ContentValues values = new ContentValues();
		values.put("id", dashboardCategory.getId());
		values.put("cname", dashboardCategory.getCategoryName());
		values.put("dashboard_id", dashboardCategory.getDashboardId());

		SQLiteDatabase db = this.dbOpen();
		db.insert(DASHBOARD_CATEGORY_TABLE, null, values);

		values.clear();
		values = null;

		this.dbClose(db);
	}

	public int updateDashboardCategory(DashboardCategory dashboardCategory) {
		ContentValues values = new ContentValues();
		values.put("cname", dashboardCategory.getCategoryName());
		values.put("dashboard_id", dashboardCategory.getDashboardId());

		SQLiteDatabase db = this.dbOpen();
		int isUpadated = db.update(DASHBOARD_CATEGORY_TABLE, values, "id = ?",
				new String[] { String.valueOf(dashboardCategory.getId()) });
		values.clear();
		values = null;

		this.dbClose(db);
		return isUpadated;
	}

	public void deleteDashboardCategory(DashboardCategory dashboardCategory) {
		SQLiteDatabase db = this.dbOpen();
		db.delete(DASHBOARD_CATEGORY_TABLE, "id = ?",
				new String[] { String.valueOf(dashboardCategory.getId()) });
		this.dbClose(db);
	}

	public String getUpdateDate() {
		SQLiteDatabase db = this.dbOpen();
		Cursor cursor = db.query(SYS_UPDATE_TABLE, new String[] { "id", "name",
				"last_update_date" }, "name = ?",
				new String[] { "dashboardcategory" }, null, null, null);

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
				new String[] { "dashboardcategory" });
		values.clear();
		values = null;

		this.dbClose(db);
	}

	public DashboardCategory getDashboardCategoryByName(String cateoryName) {
		SQLiteDatabase db = this.dbOpen();

		Cursor cursor = db.query(DASHBOARD_CATEGORY_TABLE, new String[] { "id",
				"cname", "dashboard_id" }, "cname = ?",
				new String[] { String.valueOf(cateoryName) }, null, null, null);
		DashboardCategory dashboardCategory = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				dashboardCategory = new DashboardCategory(
						Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2));
			}
		}

		cursor.close();
		this.dbClose(db);
		return dashboardCategory;
	}

}
