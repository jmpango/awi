package org.awi.ui.server.dao;

import org.awi.ui.server.util.BuddyContants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDAO extends SQLiteOpenHelper {
	public static final String SYS_UPDATE_TABLE = "sys_update";
	
	public BaseDAO(Context context) {
		super(context, BuddyContants.DATABASE_NAME, null, BuddyContants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_DASHBOARD_TABLE = "CREATE TABLE dashboard(id INTEGER PRIMARY KEY, dname TEXT, tagline TEXT)";
		String CREATE_DASHBOARD_CATEGORY_TABLE = "CREATE TABLE dashboardcategory(id INTEGER PRIMARY KEY, cname TEXT, dashboard_id, FOREIGN KEY(dashboard_id) REFERENCES dashboard(id))";
		String CREATE_BUDDY_TABLE = "CREATE TABLE buddy(id INTEGER PRIMARY KEY, name TEXT, tagline TEXT, address TEXT, telphone TEXT, email TEXT, fax TEXT, url TEXT, dashboard_category_id INTEGER, FOREIGN KEY(dashboard_category_id) REFERENCES dashboardcategory(id))";
		String CREATE_BUDDY_LOCATION_TABLE = "CREATE TABLE buddy_location(id INTEGER PRIMARY KEY, location_name TEXT, buddy_id INTEGER, FOREIGN KEY(buddy_id) REFERENCES buddy(id))";
		String CREATE_BUDDY_SEARCH_TAG_TABLE = "CREATE TABLE buddy_search_tag(id INTEGER PRIMARY KEY, searchtag TEXT, buddy_id INTEGER, FOREIGN KEY(buddy_id) REFERENCES buddy(id))";
		String CREATE_BUDDY_RATING_TABLE = "CREATE TABLE buddy_rating(id INTEGER PRIMARY KEY AUTOINCREMENT, rate INTEGER, buddy_id INTEGER, FOREIGN KEY(buddy_id) REFERENCES buddy(id))";
		String CREATE_SEARCH_TERM_TABLE = "CREATE TABLE search_terms(id INTEGER PRIMARY KEY AUTOINCREMENT, term TEXT)";
		String CREATE_BUDDY_USAGE_TABLE = "CREATE TABLE buddy_usage(id INTEGER PRIMARY KEY AUTOINCREMENT, page_hits INTEGER, call_hits INTEGER, url_hits INTEGER, email_hits INTEGER , buddy_id INTEGER, FOREIGN KEY(buddy_id) REFERENCES buddy(id))";
		String CREATE_BUDDY_COMMENT_TABLE = "CREATE TABLE buddy_comments(id INTEGER PRIMARY KEY, comment TEXT, owner TEXT, post_date TEXT, status INT , buddy_id INTEGER, FOREIGN KEY(buddy_id) REFERENCES buddy(id))";
		String CREATE_ADVERT_TABLE = "CREATE TABLE adverts(id INTEGER PRIMARY KEY, name TEXT, tagline TEXT, url TEXT, telphone TEXT, address TEXT, image_name TEXT, parent_id INTEGER)";
		String CREATE_SYS_UPDATE_TABLE = "CREATE TABLE sys_update(id INTEGER PRIMARY KEY, name TEXT, last_update_date TEXT)";
		
		String INSERT_INTO_SYS_UPDATE_TABLE_DASHBOARD = "INSERT INTO sys_update(id,name,last_update_date) VALUES (1, 'dashboard', '2013-04-01')";
		String INSERT_INTO_SYS_UPDATE_TABLE_DASHBOARDCAT = "INSERT INTO sys_update(id,name,last_update_date) VALUES (2, 'dashboardcategory', '2013-04-01')";
		String INSERT_INTO_SYS_UPDATE_TABLE_BUDDY = "INSERT INTO sys_update(id,name,last_update_date) VALUES (3, 'buddy', '2013-04-01')";
		String INSERT_INTO_SYS_UPDATE_TABLE_LOCATION = "INSERT INTO sys_update(id,name,last_update_date) VALUES (4, 'buddylocation', '2013-04-01')";
		String INSERT_INTO_SYS_UPDATE_TABLE_SEARCHTAG = "INSERT INTO sys_update(id,name,last_update_date) VALUES (5, 'buddysearchtag', '2013-04-01')";
		String INSERT_INTO_SYS_UPDATE_TABLE_COMMENT = "INSERT INTO sys_update(id,name,last_update_date) VALUES (6, 'comment', '2013-04-01')";
		String INSERT_INTO_SYS_UPDATE_TABLE_ADVERT = "INSERT INTO sys_update(id,name,last_update_date) VALUES (7, 'advert', '2013-04-01')";
		String INSERT_INTO_SYS_UPDATE_TABLE_RATING = "INSERT INTO sys_update(id,name,last_update_date) VALUES (8, 'rating', '2013-04-01')";
		
		db.execSQL(CREATE_DASHBOARD_TABLE);
		db.execSQL(CREATE_DASHBOARD_CATEGORY_TABLE);
		db.execSQL(CREATE_BUDDY_TABLE);
		db.execSQL(CREATE_BUDDY_LOCATION_TABLE);
		db.execSQL(CREATE_BUDDY_SEARCH_TAG_TABLE);
		db.execSQL(CREATE_BUDDY_RATING_TABLE);
		db.execSQL(CREATE_SEARCH_TERM_TABLE);
		db.execSQL(CREATE_BUDDY_USAGE_TABLE);
		db.execSQL(CREATE_BUDDY_COMMENT_TABLE);
		db.execSQL(CREATE_ADVERT_TABLE);
		db.execSQL(CREATE_SYS_UPDATE_TABLE);
		
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_DASHBOARD);
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_DASHBOARDCAT);
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_BUDDY);
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_LOCATION);
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_SEARCHTAG);
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_COMMENT);
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_ADVERT);
		db.execSQL(INSERT_INTO_SYS_UPDATE_TABLE_RATING);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS buddy_comments");
		db.execSQL("DROP TABLE IF EXISTS buddy_usage");
		db.execSQL("DROP TABLE IF EXISTS buddy_rating");
		db.execSQL("DROP TABLE IF EXISTS search_terms");
		db.execSQL("DROP TABLE IF EXISTS buddy_search_tag");
		db.execSQL("DROP TABLE IF EXISTS buddy_location");
		db.execSQL("DROP TABLE IF EXISTS adverts");
		db.execSQL("DROP TABLE IF EXISTS buddy");
		db.execSQL("DROP TABLE IF EXISTS dashboardcategory");
		db.execSQL("DROP TABLE IF EXISTS dashboard");
		
        onCreate(db);
	}
	
	public SQLiteDatabase dbOpen(){
		return this.getWritableDatabase();
	}
	
	public void dbClose(SQLiteDatabase db){
		db.close();
	}

}
