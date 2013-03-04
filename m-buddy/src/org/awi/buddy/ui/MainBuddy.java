package org.awi.buddy.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.awi.buddy.model.DashBoard;
import org.awi.buddy.server.MainBuddyDataBinder;
import org.awi.buddy.util.DataLoader;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainBuddy extends Activity {
	private List<DashBoard> dashBoardList;
	private EditText searchBox;
	private MainBuddy mainBuddy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_buddy_layout);
		this.mainBuddy = this;

		try {
			this.dashBoardList = DataLoader.getMainBuddyData(getAssets().open(
					"dashboard.xml"));
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}

		hideMsgLabel();

		final ListView listView = (ListView) findViewById(R.id.dashboard_list_view);
		listView.setAdapter(new MainBuddyDataBinder(this, dashBoardList));

		searchBox = (EditText) findViewById(R.id.search_box);
		searchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int start, int before,
					int count) {
				String searchString = searchBox.getText().toString();
				int textLength = searchString.length();

				List<DashBoard> newSearchedDashBoard = new ArrayList<DashBoard>();

				for (DashBoard dashBoard : dashBoardList) {

					if (textLength <= dashBoard.getName().length()) {
						if (searchString.equalsIgnoreCase(dashBoard.getName()
								.substring(0, textLength))) {
							newSearchedDashBoard.add(dashBoard);
						}
					}
				}

				if (newSearchedDashBoard.size() == 0) {
					showMsgLabel("No search Found");
					listView.setAdapter(new MainBuddyDataBinder(mainBuddy,
							dashBoardList));
				} else {
					hideMsgLabel();
					listView.setAdapter(new MainBuddyDataBinder(mainBuddy,
							newSearchedDashBoard));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	// set listner
	/*
	 * list.setOnItemClickListener(new OnItemClickListener() {
	 * 
	 * public void onItemClick(AdapterView<?> parent, View view, int position,
	 * long id) {
	 * 
	 * Intent i = new Intent(); i.setClass(WeatherActivity.this,
	 * SampleActivity.class);
	 * 
	 * // parameters i.putExtra("position", String.valueOf(position + 1));
	 * 
	 * selected item parameters 1. City name 2. Weather 3. Wind speed 4.
	 * Temperature 5. Weather icon
	 * 
	 * i.putExtra("city", weatherDataCollection.get(position).get(KEY_CITY));
	 * i.putExtra("weather",
	 * weatherDataCollection.get(position).get(KEY_CONDN));
	 * i.putExtra("windspeed",
	 * weatherDataCollection.get(position).get(KEY_SPEED));
	 * i.putExtra("temperature",
	 * weatherDataCollection.get(position).get(KEY_TEMP_C)); i.putExtra("icon",
	 * weatherDataCollection.get(position).get(KEY_ICON));
	 * 
	 * // start the sample activity startActivity(i); } });
	 */

	private void hideMsgLabel() {
		TextView msgLabel = (TextView) findViewById(R.id.msg_label);
		msgLabel.setVisibility(View.GONE);
	}

	private void showMsgLabel(String msg) {
		TextView msgLabel = (TextView) findViewById(R.id.msg_label);
		msgLabel.setVisibility(View.VISIBLE);
		msgLabel.setText(msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_buddy_layout, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

}
