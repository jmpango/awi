package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.server.service.SearchService;
import org.awi.ui.server.service.impl.SearchServiceImpl;
import org.awi.ui.server.util.BuddyContants;
import org.awi.ui.server.viewbinders.ListingUIBinder;

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

public class ListingUI extends BaseActivity implements OnItemClickListener,
		TextWatcher{

	private  ListView listView;
	private String pageTitle;
	private List<Buddy>  listingList;
	private List<Buddy>  tempListingList;
	private TextView titleTextView;
	private ListingSearchAsyncTask listingSearchAsyncTask;
	private SearchService searchService;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listingui_layout);

		Bundle bundle = this.getIntent().getExtras();
		this.pageTitle = bundle.getString(BuddyContants.PAGE_NAME).toUpperCase();
		this.tempListingList = bundle.getParcelableArrayList(BuddyContants.BUDDY_LISTING);
		
		this.listingList = tempListingList;
		prepareListingUI();
	}

	private void prepareListingUI() {
		hideMsgBox();
		this.titleTextView = (TextView) findViewById(R.id.pageTitle);
		this.listView = (ListView) findViewById(R.id.listing_list_view);
		this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);

		titleTextView.setText(pageTitle + " LISTING");

		this.dashBoardSearchBox.addTextChangedListener(this);
		this.listView.setOnItemClickListener(this);

		listView.setAdapter(new ListingUIBinder(ListingUI.this, tempListingList));
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
		Buddy buddy = (Buddy) tempListingList.get(position);
		Intent intent = new Intent(ListingUI.this, DetailUI.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(BuddyContants.DEFAULT_BUDDY, buddy);
		intent.putExtras(bundle);
		
		ListingUI.this.finish();
		startActivity(intent);
	}

	private class ListingSearchAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			tempListingList = new ArrayList<Buddy>();
			if(searchService == null){
				searchService = new SearchServiceImpl();
			}
			tempListingList = searchService.listingSearch(dashBoardSearchBox.getText().toString(), listingList);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (tempListingList.size() == 0)
				showMsgBox(BuddyContants.NO_RESULT_FOUND);
			listView.setAdapter(new ListingUIBinder(ListingUI.this, tempListingList));
		}
	}
}
