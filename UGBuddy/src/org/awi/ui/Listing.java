package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.Globals;
import org.awi.ui.server.databinder.BuddyDataBinder;
import org.awi.ui.server.service.impl.BuddyServiceImpl;
import org.awi.ui.server.util.BuddyContants;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Listing extends BaseActivity implements OnItemClickListener,
		TextWatcher {

	private Listing listing;
	private ListView listingListView;
	private ListingSearchAsyncTask listingSearchAsyncTask;

	private List<Buddy> listingList, tempListingList;
	private String parentTag;

	private TextView pageTitle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listing_layout);

		Bundle bundle = this.getIntent().getExtras();
		this.parentTag = bundle.getString(BuddyContants.PAGE_NAME)
				.toLowerCase();
		this.listing = this;

		prepareListingUI();
	}

	private void prepareListingUI() {
		hideMsgBox();

		this.listingListView = (ListView) findViewById(R.id.listing_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
		this.pageTitle = (TextView) findViewById(R.id.pageTitle);
		pageTitle.setText(BuddyContants.CONVERT_FIRST_LETTER_CAPS(parentTag)
				+ " Listing");

		this.dashBoardSearchBox.addTextChangedListener(this);
		this.listingListView.setOnItemClickListener(this);

		if (buddyService == null) {
			buddyService = BuddyServiceImpl.getInstance();
		}

		this.listingList = Globals.getInstance().getBuddy(parentTag);
		this.tempListingList = listingList;
		listingListView.setAdapter(new BuddyDataBinder(listing, listingList));

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
		Buddy buddy = tempListingList.get(position);
		Intent intent = new Intent(listing, Details.class);
		intent.putExtra(BuddyContants.DEFAULT_BUDDY, buddy);
		gabaggeCollector();
		startActivity(intent);
	}

	private class ListingSearchAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			tempListingList = new ArrayList<Buddy>();
			tempListingList = buddyService.searchBuddies(dashBoardSearchBox
					.getText().toString(), listingList);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (tempListingList.size() == 0)
				showMsgBox(BuddyContants.NO_RESULT_FOUND);

			listingListView.setAdapter(new BuddyDataBinder(listing,
					tempListingList));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected void gabaggeCollector() {
		this.listingListView = null;

		if (listingSearchAsyncTask != null) {
			listingSearchAsyncTask.cancel(true);
			this.listingSearchAsyncTask = null;
		}

		if (listingList != null)
			this.listingList.clear();

		if (tempListingList != null)
			this.tempListingList.clear();

		this.listingList = null;
		this.tempListingList = null;
		this.parentTag = null;
		this.pageTitle = null;
		this.listing = null;
		Listing.this.finish();
	}
}
