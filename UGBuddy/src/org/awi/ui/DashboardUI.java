package org.awi.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.DashBoard;
import org.awi.ui.model.DashboardCategory;
import org.awi.ui.server.service.AlertPositiveListener;
import org.awi.ui.server.service.BuddyService;
import org.awi.ui.server.service.DashboardCategoryService;
import org.awi.ui.server.service.DashboardService;
import org.awi.ui.server.service.SearchService;
import org.awi.ui.server.service.impl.BuddyServiceImpl;
import org.awi.ui.server.service.impl.DashboardCategoryServiceImpl;
import org.awi.ui.server.service.impl.DashboardServiceImpl;
import org.awi.ui.server.service.impl.SearchServiceImpl;
import org.awi.ui.server.util.BuddyContants;
import org.awi.ui.server.util.RadioDialog;
import org.awi.ui.server.viewbinders.DashboardUIBinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * References the UI with the app dashboard.
 * 
 * @author Jonathan
 * 
 */
public class DashboardUI extends BaseActivity implements TextWatcher,
		OnItemClickListener, AlertPositiveListener {
	private ListView dashboardListView;
	private List<DashBoard> dashboardList;
	private List<DashBoard> tempDashboardList;
	private DashboardService dashboardService;
	private DashboardCategoryService dashboardCategoryService;
	private BuddyService buddyService;
	private SearchService searchService;
	private DashboardSearchAsyncTask dashboardSearchAsyncTask;
	private String[] dashboardCategories;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboardui_layout);
		hideMsgBox();

		dashboardService = new DashboardServiceImpl(this);

		tempDashboardList = dashboardService.getAllDashboards();
		Collections.sort(tempDashboardList);
		dashboardList = tempDashboardList;

		this.dashboardListView = (ListView) findViewById(R.id.dashboard_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
		this.homeBtn = (ImageButton) findViewById(R.id.home_btn);
		this.advancedSearchBtn = (ImageButton) findViewById(R.id.advanced_search_btn);
		this.searchBox = (EditText) findViewById(R.id.txt_search_query);

		dashboardListView.setAdapter(new DashboardUIBinder(DashboardUI.this,
				tempDashboardList));
		this.dashBoardSearchBox.addTextChangedListener(this);
		this.dashboardListView.setOnItemClickListener(this);
	}

	private class DashboardSearchAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			if (tempDashboardList.size() == 0)
				showMsgBox(BuddyContants.NO_RESULT_FOUND);
			Collections.sort(tempDashboardList);
			dashboardListView.setAdapter(new DashboardUIBinder(
					DashboardUI.this, tempDashboardList));

			hideMsgBox();
			try {
				this.finalize();
				this.cancel(true);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			tempDashboardList = new ArrayList<DashBoard>();
			if(searchService == null){
				searchService = new SearchServiceImpl();
			}
			tempDashboardList = searchService.dashboardSearch(dashBoardSearchBox.getText().toString(), dashboardList);
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_layout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about_update:
			Intent intent = new Intent(DashboardUI.this, UpdateUI.class);
			DashboardUI.this.finish();
			startActivity(intent);
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		this.dashboardSearchAsyncTask = new DashboardSearchAsyncTask();
		dashboardSearchAsyncTask.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		DashBoard selectedDashboard = tempDashboardList.get(position);
		dashboardCategories = null;

		dashboardCategoryService = new DashboardCategoryServiceImpl(
				DashboardUI.this);

		List<DashboardCategory> dCategoryList = dashboardCategoryService
				.getAllDashboardCategoryByDashboardId(selectedDashboard.getId());
		
		if (!dCategoryList.isEmpty()) {
			dashboardCategories = new String[dCategoryList.size()];
			for (int i = 0; i < dCategoryList.size(); i++) {
				dashboardCategories[i] = dCategoryList.get(i).getCategoryName();
			}
		}

		FragmentManager manager = getSupportFragmentManager();
		RadioDialog alert = new RadioDialog(dashboardCategories,
				selectedDashboard.getName() + " Listing", "view");

		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		alert.setArguments(bundle);
		alert.show(manager, "alert_dialog_radio");
	}

	@Override
	public void onPositiveClick(int position) {
		if(dashboardCategories != null){
			String categoryName = dashboardCategories[position];
			DashboardCategory dashboardCategory = dashboardCategoryService.getDashboardCategoryByName(categoryName);
			buddyService = new BuddyServiceImpl(DashboardUI.this);
			
			List<Buddy> buddies = buddyService.getAllBuddiesByDashboardCategoryId(dashboardCategory.getId());
			Collections.sort(buddies);
			
			if(!buddies.isEmpty()){
				Intent intent = new Intent(DashboardUI.this, ListingUI.class);
				Bundle bundle = new Bundle();
				bundle.putString(BuddyContants.PAGE_NAME, 	dashboardCategories[position]);
				bundle.putParcelableArrayList(BuddyContants.BUDDY_LISTING, (ArrayList<? extends Parcelable>) buddies);
				intent.putExtras(bundle);
				
				DashboardUI.this.finish();
				startActivity(intent);
			}
			
		}
	}
}
