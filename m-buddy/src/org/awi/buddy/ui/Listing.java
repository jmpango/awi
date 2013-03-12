package org.awi.buddy.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.buddy.model.Buddy;
import org.awi.buddy.server.binder.BuddyDataBinder;
import org.awi.buddy.server.service.BuddyService;
import org.awi.buddy.server.service.impl.BuddyServiceImpl;
import org.awi.buddy.server.util.BuddyContants;

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
	private ListingAsyncTask listinAsyncTask;
	private ListingSearchAsyncTask listingSearchAsyncTask;
	private BuddyService listingService;

	private List<Buddy> listingList, tempListingList;
	private String xmlFileName;
	private String parentTag;
	
	private TextView pageTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listing_layout);
		Bundle bundle = this.getIntent().getExtras();
		this.parentTag = bundle.getString(BuddyContants.PAGE_NAME).toLowerCase();
		this.xmlFileName = bundle.getString(BuddyContants.PAGE_NAME).toLowerCase()
				+ BuddyContants.DEFAULT_XML_EXTENTION;
		this.listing = this;

		prepareListingUI();

	}

	private void prepareListingUI() {
		hideMsgBox();

		this.listingListView = (ListView) findViewById(R.id.listing_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
		this.pageTitle = (TextView) findViewById(R.id.pageTitle);
		pageTitle.setText(parentTag + " Listing");

		this.dashBoardSearchBox.addTextChangedListener(this);
		this.listingListView.setOnItemClickListener(this);

		this.listinAsyncTask = new ListingAsyncTask();
		listinAsyncTask.execute();
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
		this.listingSearchAsyncTask = new ListingSearchAsyncTask();
		listingSearchAsyncTask.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Buddy buddy = tempListingList.get(position);
		System.out.println(">:: " + buddy.getLocations().get(0).getName());
	}

	private class ListingAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			if (listingService == null)
				listingService = BuddyServiceImpl.getInstance(getAssets(),
						xmlFileName, parentTag);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			tempListingList = listingService.getBuddies();
			hideProgressBar();

			if (listingList == null)
				listingList = tempListingList;

			listingListView.setAdapter(new BuddyDataBinder(listing,
					tempListingList));
		}

		@Override
		protected void onPreExecute() {
			showProgressBar(listing);
		}

	}

	private class ListingSearchAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			tempListingList = listingService.searchBuddies(dashBoardSearchBox
					.getText().toString(), listingList);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (tempListingList.size() == 0) {
				showMsgBox("No search Found");
				listingListView.setAdapter(new BuddyDataBinder(listing,
						new ArrayList<Buddy>()));
			} else {
				listingListView.setAdapter(new BuddyDataBinder(listing,
						tempListingList));
			}
		}

	}
}
