package org.awi.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.BuddyLocation;
import org.awi.ui.model.BuddySearchTag;
import org.awi.ui.server.service.BackHomeButtonListener;
import org.awi.ui.server.util.BuddyContants;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Listing extends BaseActivity implements OnItemClickListener,
		TextWatcher, BackHomeButtonListener {

	private ListingSearchAsyncTask listingSearchAsyncTask;
	private List<Buddy> listingList, appTempList;
	private String parentTag;
	private TextView pageTitle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listing_layout);

		Bundle bundle = this.getIntent().getExtras();
		this.parentTag = bundle.getString(BuddyContants.PAGE_NAME)
				.toUpperCase();
		this.listingList = bundle
				.getParcelableArrayList(BuddyContants.BUDDY_LISTING);

		/*if (this.buddyService == null) {
			this.buddyService = BuddyServiceImpl.getInstance();
		}*/

		prepareListingUI();
	}

	private void prepareListingUI() {
		hideMsgBox();

		//this.appListView = (ListView) findViewById(R.id.listing_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
		this.pageTitle = (TextView) findViewById(R.id.pageTitle);
		this.homeBtn = (ImageButton) findViewById(R.id.home_btn);
		this.advancedSearchBtn = (ImageButton) findViewById(R.id.advanced_search_btn);

		backHomeBtnClickHandler();

		pageTitle.setText(parentTag + " LISTING");

		this.dashBoardSearchBox.addTextChangedListener(this);
		//this.appListView.setOnItemClickListener(this);

		this.appTempList = listingList;
	//	appListView.setAdapter(new BuddyDataBinder(Listing.this, listingList));

		advancedSearch();
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (listingSearchAsyncTask != null) {
			listingSearchAsyncTask.cancel(true);
			this.listingSearchAsyncTask = null;
		}

		this.listingSearchAsyncTask = new ListingSearchAsyncTask();
		listingSearchAsyncTask.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Buddy buddy = (Buddy) appTempList.get(position);
		Intent intent = new Intent(Listing.this, Details.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(BuddyContants.DEFAULT_BUDDY, buddy);

		intent.putExtras(bundle);
		gabaggeCollector();
		startActivity(intent);
	}

	private class ListingSearchAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			appTempList = new ArrayList<Buddy>();
			//appTempList = buddyService.searchBuddies(dashBoardSearchBox
				//	.getText().toString(), listingList);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (appTempList.size() == 0)
				showMsgBox(BuddyContants.NO_RESULT_FOUND);

		//	appListView.setAdapter(new BuddyDataBinder(Listing.this,
			//		appTempList));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void gabaggeCollector() {
	//	baseActivityGabaggeCollector();
		//this.appListView = null;

		if (listingSearchAsyncTask != null) {
			listingSearchAsyncTask.cancel(true);
			this.listingSearchAsyncTask = null;
		}

		if (listingList != null)
			this.listingList.clear();

		if (appTempList != null)
			this.appTempList.clear();

		this.listingList = null;
		this.appTempList = null;
		this.parentTag = null;
		this.pageTitle = null;

		try {
			Listing.this.finalize();
		} catch (Throwable e) {
		}
	}

	@Override
	public void backHomeBtnClickHandler() {
		homeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Listing.this, DashboardUI.class);
				gabaggeCollector();
				startActivity(intent);
			}
		});

	}

	@Override
	public void clossApplicationBtnClickHandler() {

	}

	public void advancedSearch() {
		advancedSearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(Listing.this);
				View searchBoxView = layoutInflater.inflate(
						R.layout.advanced_search_layout, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						Listing.this);
				alertDialogBuilder.setView(searchBoxView);

				searchBox = (EditText) searchBoxView
						.findViewById(R.id.txt_search_query);

				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Search",
								new DialogInterface.OnClickListener() {

									@SuppressWarnings("null")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String searchQuery = searchBox
												.getText().toString()
												.toLowerCase();
										List<Buddy> searchList = new ArrayList<Buddy>();

									/*	if (Globals.getInstance() == null) {
											Globals.getInstance();
										}*/
										HashMap<String, List<Buddy>> datas = /*Global.getDataz()*/ null;

										if (!datas.isEmpty()) {
											for (List<Buddy> buddies : datas
													.values()) {
												for (Buddy buddy : buddies) {

													// -------- BY Buddy Name
													// ---------//
													String searchByName = buddy
															.getName()
															.toLowerCase();
													if (searchByName
															.contains(searchQuery)) {
														if (!searchList
																.contains(buddy)) {
															searchList
																	.add(buddy);
															// break;
														}
													} else {
														if (isExisting(
																searchByName,
																searchQuery)) {
															if (!searchList
																	.contains(buddy)) {
																searchList
																		.add(buddy);
																// break;
															}
														} else {
															boolean isAvailable = false;
															for (BuddyLocation loc : buddy
																	.getLocations()) {
																String locationName = loc
																		.getName()
																		.toLowerCase();
																if (locationName
																		.contains(searchQuery)) {
																	if (!searchList
																			.contains(buddy)) {
																		searchList
																				.add(buddy);
																		isAvailable = true;
																		// break;
																	}
																} else {
																	if (isExisting(
																			locationName,
																			searchQuery)) {
																		if (!searchList
																				.contains(buddy)) {
																			searchList
																					.add(buddy);
																			isAvailable = true;
																			// break;
																		}
																	}
																}
															}

															if (!isAvailable) {
																for (BuddySearchTag sTag : buddy
																		.getSearchTags()) {
																	String locationName = sTag
																			.getName()
																			.toLowerCase();
																	if (locationName
																			.contains(searchQuery)) {
																		if (!searchList
																				.contains(buddy)) {
																			searchList
																					.add(buddy);
																			// break;
																		}
																	} else {
																		if (isExisting(
																				locationName,
																				searchQuery)) {
																			if (!searchList
																					.contains(buddy)) {
																				searchList
																						.add(buddy);
																				// break;
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}

										if (searchList.isEmpty())
											showMsgBox(searchQuery
													+ " has no record. Try another search term.");
										else {
											Intent intent = new Intent(
													getApplication(),
													Listing.class);
											Bundle bundle = new Bundle();
											bundle.putString(
													BuddyContants.PAGE_NAME,
													"Advanced Search Results");
											Collections.sort(searchList);
											bundle.putParcelableArrayList(
													BuddyContants.BUDDY_LISTING,
													(ArrayList<? extends Parcelable>) searchList);
											intent.putExtras(bundle);
											intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
											gabaggeCollector();
											searchList = null;
											startActivity(intent);
										}
									}

									private boolean isExisting(
											String searchWord,
											String searchQuery) {
										String[] splitDemilter = searchQuery
												.split("\\s+");
										for (int index = 0; index < splitDemilter.length; index++) {
											if (searchWord
													.contains(splitDemilter[index])) {
												return true;
											}
										}
										return false;
									}

								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();

			}
		});
	}
	
	public void showToast(String msg) {
		Toast.makeText(Listing.this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.about_ug:
			showToast("About Uganda is Selected");
			return true;
		case R.id.about_update:
			showToast("Update is Selected");
			return true;
		case R.id.about_ug_buddy:
			showToast("Abour UGBuddy is Selected");
			return true;
		case R.id.important_numbers:
			showToast("IMportant Nos is Selected");
			return true;
		case R.id.practical_info:
			showToast("Practical Info is Selected");
			return true;
		case R.id.help:
			showToast("Update is Selected");
			return true;
		}
		return false;
	}
}
