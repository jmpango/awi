package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.server.databinder.BuddyDataBinder;
import org.awi.ui.server.service.BackHomeButtonListener;
import org.awi.ui.server.service.impl.BuddyServiceImpl;
import org.awi.ui.server.util.BuddyContants;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;

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
		this.listingList = bundle.getParcelableArrayList(BuddyContants.BUDDY_LISTING);
		
		if(this.buddyService == null){
			this.buddyService = BuddyServiceImpl.getInstance();
		}
		
		prepareListingUI();
	}

	private void prepareListingUI() {
		hideMsgBox();

		this.appListView = (ListView) findViewById(R.id.listing_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
		this.pageTitle = (TextView) findViewById(R.id.pageTitle);
		this.homeBtn = (ImageButton) findViewById(R.id.home_btn);
		this.advancedSearchBtn = (ImageButton) findViewById(R.id.advanced_search_btn);
		this.locationSearchBox = (AutoCompleteTextView) findViewById(R.id.txt_search_query);

		backHomeBtnClickHandler();

		pageTitle.setText(parentTag + " LISTING");

		this.dashBoardSearchBox.addTextChangedListener(this);
		this.appListView.setOnItemClickListener(this);

		this.appTempList = listingList;
		appListView.setAdapter(new BuddyDataBinder(Listing.this,
				listingList));
		
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
			appTempList = buddyService.searchBuddies(dashBoardSearchBox.getText().toString(), listingList);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (appTempList.size() == 0)
				showMsgBox(BuddyContants.NO_RESULT_FOUND);

			appListView.setAdapter(new BuddyDataBinder(Listing.this,
					appTempList));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void gabaggeCollector() {
		this.appListView = null;

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
		Listing.this.finish();
	}

	@Override
	public void backHomeBtnClickHandler() {
		homeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Listing.this, MainBuddy.class);
				gabaggeCollector();
				startActivity(intent);
			}
		});

	}

	@Override
	public void clossApplicationBtnClickHandler() {

	}
}
