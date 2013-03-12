package org.awi.buddy.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.buddy.model.DashBoard;
import org.awi.buddy.server.binder.DashboardDataBinder;
import org.awi.buddy.server.service.AlertPositiveListener;
import org.awi.buddy.server.service.DashboardService;
import org.awi.buddy.server.service.impl.DashboardServiceImpl;
import org.awi.buddy.server.util.BuddyContants;
import org.awi.buddy.server.util.BuddyDialogRadio;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainBuddy extends BaseActivity implements OnItemClickListener,
		TextWatcher, AlertPositiveListener {

	private ListView dashBoardListView;
	private MainBuddy mainBuddy;
	private DashboardAsyncTask dashboardAsyncTask;
	private DashboardSearchAsyncTask dashboardSearchAsyncTask;
	private DashboardService dashBoardService;
	private String[] popUpNameListing;
	// TODO add reference to the onSearchListener for the advanced search
	// listener.
	private List<DashBoard> dashBoardList, tempDashboardList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_buddy_layout);

		this.mainBuddy = this;

		prepareUI();
	}

	private void prepareUI() {
		hideMsgBox();

		this.dashBoardListView = (ListView) findViewById(R.id.dashboard_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
		
		this.dashBoardSearchBox.addTextChangedListener(this);
		this.dashBoardListView.setOnItemClickListener(this);

		this.dashboardAsyncTask = new DashboardAsyncTask();
		dashboardAsyncTask.execute();
	}

	private class DashboardAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			showProgressBar(mainBuddy);
		}

		@Override
		protected void onPostExecute(Void result) {
			tempDashboardList = dashBoardService.getDashBoardData();
			hideProgressBar();
			if (dashBoardList == null)
				dashBoardList = tempDashboardList;

			dashBoardListView.setAdapter(new DashboardDataBinder(mainBuddy,
					tempDashboardList));
		}

		@Override
		protected Void doInBackground(Void... params) {
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
			if (tempDashboardList.size() == 0) {
				showMsgBox("No search Found");
				dashBoardListView.setAdapter(new DashboardDataBinder(mainBuddy,
						new ArrayList<DashBoard>()));
			} else {
				dashBoardListView.setAdapter(new DashboardDataBinder(mainBuddy,
						tempDashboardList));
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			tempDashboardList = dashBoardService.searchDashBoardData(
					dashBoardSearchBox.getText().toString(), dashBoardList);
			return null;
		}
	}

	@Override
	public void onPositiveClick(int position) {
		Intent intent = new Intent(mainBuddy, Listing.class);
		Bundle bundle  = new Bundle();
		bundle.putString(BuddyContants.PAGE_NAME, popUpNameListing[position]);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
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
		popUpNameListing = getData(getAssets(), BuddyContants.LISTING, dBoard
				.getName().toLowerCase() + "Listing.xml");
		
		FragmentManager manager = getFragmentManager();

		BuddyDialogRadio alert = new BuddyDialogRadio(popUpNameListing,
				dBoard.getName() + " Listing", "view");

		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		alert.setArguments(bundle);
		alert.show(manager, "alert_dialog_radio");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (dashboardAsyncTask != null)
			dashboardAsyncTask.cancel(true);

		if (dashboardSearchAsyncTask != null)
			dashboardSearchAsyncTask.cancel(true);

	}
	/*
	 * @Override public void gabaggeCollectord() { if (dashboardAsyncTask !=
	 * null) this.dashboardAsyncTask.cancel(true); if (dashboardSearchAsyncTask
	 * != null) this.dashboardAsyncTask.cancel(true);
	 * 
	 * this.msgLabel = null; this.dashBoardSearchBox = null;
	 * this.dashBoardListView = null; this.mainBuddy = null;
	 * this.popUpNameListing = null;
	 * 
	 * this.tempDashboardList.clear(); this.tempDashboardList = null; }
	 */
}
