package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.DashBoard;
import org.awi.ui.model.Globals;
import org.awi.ui.server.databinder.DashboardDataBinder;
import org.awi.ui.server.service.AlertPositiveListener;
import org.awi.ui.server.service.BackHomeButtonListener;
import org.awi.ui.server.service.DashboardService;
import org.awi.ui.server.service.impl.BuddyServiceImpl;
import org.awi.ui.server.service.impl.DashboardServiceImpl;
import org.awi.ui.server.util.BuddyContants;
import org.awi.ui.server.util.BuddyDialogRadio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainBuddy extends BaseActivity implements OnItemClickListener,
		TextWatcher, AlertPositiveListener, BackHomeButtonListener {

	private ListView dashBoardListView;
	private DashboardAsyncTask dashboardAsyncTask;
	private DashboardSearchAsyncTask dashboardSearchAsyncTask;
	private DashboardService dashBoardService;
	private String[] popUpNameListing;
	private List<DashBoard> dashBoardList, tempDashboardList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_buddy_layout);
		prepareUI();
	}

	private void prepareUI() {
		hideMsgBox();

		this.dashBoardListView = (ListView) findViewById(R.id.dashboard_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
		this.homeBtn = (ImageButton) findViewById(R.id.home_btn);
		this.advancedSearchBtn = (ImageButton) findViewById(R.id.advanced_search_btn);
		this.searchBox = (EditText) findViewById(R.id.txt_search_query);
		this.locationSearchBox = (AutoCompleteTextView) findViewById(R.id.txt_search_query);
		
		backHomeBtnClickHandler();

		this.dashBoardSearchBox.addTextChangedListener(this);
		this.dashBoardListView.setOnItemClickListener(this);

		this.dashboardAsyncTask = new DashboardAsyncTask();
		dashboardAsyncTask.execute();
		
		advancedSearch();
	}

	private class DashboardAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			showProgressBar(MainBuddy.this);
		}

		@Override
		protected void onPostExecute(Void result) {
			tempDashboardList = dashBoardService.getDashBoardData();
			hideProgressBar();
			if (dashBoardList == null)
				dashBoardList = tempDashboardList;

			dashBoardListView.setAdapter(new DashboardDataBinder(
					MainBuddy.this, tempDashboardList));
			try {
				this.finalize();
				this.cancel(true);
			} catch (Throwable e) {
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			tempDashboardList = new ArrayList<DashBoard>();
			if (dashBoardService == null) {
				dashBoardService = DashboardServiceImpl
						.getInstance(getAssets());
			}
			return null;
		}
	}

	private class DashboardSearchAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			if (tempDashboardList.size() == 0)
				showMsgBox(BuddyContants.NO_RESULT_FOUND);

			dashBoardListView.setAdapter(new DashboardDataBinder(
					MainBuddy.this, tempDashboardList));
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
			tempDashboardList = dashBoardService.searchDashBoardData(
					dashBoardSearchBox.getText().toString(), dashBoardList);
			return null;
		}
	}

	@Override
	public void onPositiveClick(int position) {
		if (popUpNameListing != null) {
			Globals.getInstance();
			String name = BuddyContants.REMOVE_WHITESPACES(popUpNameListing[position]).toLowerCase();
			List<Buddy> buddies = Globals.getBuddy(name);
			if (buddies.size() == 0) {
				BuddyContants.LOAD_APP_DATA(getAssets(),
						BuddyServiceImpl.getInstance());
				buddies = Globals.getBuddy(name);
			}

			Intent intent = new Intent(MainBuddy.this, Listing.class);
			Bundle bundle = new Bundle();
			bundle.putString(BuddyContants.PAGE_NAME,
					popUpNameListing[position]);
			bundle.putParcelableArrayList(BuddyContants.BUDDY_LISTING, (ArrayList<? extends Parcelable>) buddies);
			intent.putExtras(bundle);
			gabaggeCollector();
			startActivity(intent);

		}
	}

	private void gabaggeCollector() {
		try {
			this.dashBoardListView = null;

			if (dashboardAsyncTask != null) {
				dashboardAsyncTask.cancel(true);
				dashboardAsyncTask = null;
			}

			if (dashboardSearchAsyncTask != null) {
				dashboardSearchAsyncTask.cancel(true);
				dashboardSearchAsyncTask = null;
			}

			dashBoardService = null;
			popUpNameListing = null;
			dashBoardList.clear();
			tempDashboardList.clear();
			dashBoardList = null;
			tempDashboardList = null;
			msgLabel = null;
			dashBoardSearchBox = null;
			pDialog = null;
			homeBtn = null;
			MainBuddy.this.finish();
		} catch (Throwable e) {
		}
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
		DashBoard dBoard = tempDashboardList.get(position);
		String selectItem = BuddyContants.REMOVE_WHITESPACES(dBoard.getName()).toLowerCase();
		
		popUpNameListing = getData(getAssets(), BuddyContants.LISTING, selectItem+ "/" + selectItem + ".xml");

		FragmentManager manager = getSupportFragmentManager();
		BuddyDialogRadio alert = new BuddyDialogRadio(popUpNameListing,
				dBoard.getName() + " Listing", "view");

		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		alert.setArguments(bundle);
		alert.show(manager, "alert_dialog_radio");
	}

	@Override
	public void backHomeBtnClickHandler() {
		homeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainBuddy.this, "Already Home",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void clossApplicationBtnClickHandler() {
		
	}

}
