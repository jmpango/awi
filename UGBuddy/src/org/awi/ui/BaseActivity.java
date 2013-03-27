package org.awi.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.Globals;
import org.awi.ui.server.service.BaseBuddy;
import org.awi.ui.server.service.BuddyService;
import org.awi.ui.server.service.OnSearchListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity implements BaseBuddy,
		OnSearchListener {

	public TextView msgLabel;
	public EditText dashBoardSearchBox, searchBox;
	public AutoCompleteTextView locationSearchBox;
	public final String KEY_NAME = "name";
	public ProgressDialog pDialog;
	public ImageButton homeBtn, advancedSearchBtn;
	public BuddyService buddyService;
	public ListView appListView;
	private ArrayAdapter<String> adapter;

	public BaseActivity() {
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_layout, menu);
		return true;
	}

	public String[] getData(AssetManager asserManager, String keyTag,
			String xmlFileName) {
		String[] names = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder
					.parse(asserManager.open(xmlFileName));
			NodeList listingNodeList = document.getElementsByTagName(keyTag);
			document.getDocumentElement().normalize();

			names = new String[listingNodeList.getLength()];

			for (int i = 0; i < listingNodeList.getLength(); i++) {

				Node _firstListingItemNode = listingNodeList.item(i);
				if (_firstListingItemNode.getNodeType() == Node.ELEMENT_NODE) {
					Element _firstListingItemElement = (Element) _firstListingItemNode;

					NodeList _nameList = _firstListingItemElement
							.getElementsByTagName(KEY_NAME);
					Element _firstNameElement = (Element) _nameList.item(0);
					NodeList _name = _firstNameElement.getChildNodes();
					names[i] = ((Node) _name.item(0)).getNodeValue().trim();
					;
				}
			}

		} catch (SAXException e) {
		} catch (IOException e) {
		} catch (ParserConfigurationException e) {
		}

		return names;
	}

	public void showProgressBar(Context context) {
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading...");
		pDialog.show();
	}

	public void hideProgressBar() {
		if (null != pDialog && pDialog.isShowing()) {
			pDialog.dismiss();
		}
	}

	public void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void hideMsgBox() {
		msgLabel = (TextView) findViewById(R.id.msg_label);
		msgLabel.setVisibility(View.GONE);
	}

	@Override
	public void showMsgBox(String msg) {
		msgLabel.setVisibility(View.VISIBLE);
		msgLabel.setText(msg);
	}

	@Override
	public void advancedSearch() {
		String[] locations = getResources().getStringArray(
				R.array.location_array);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, locations);

		// this.listingList = Globals.getBuddy(buddyName);

		advancedSearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(BaseActivity.this);
				View searchBoxView = layoutInflater.inflate(
						R.layout.advanced_search_layout, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BaseActivity.this);
				alertDialogBuilder.setView(searchBoxView);

				searchBox = (EditText) searchBoxView
						.findViewById(R.id.txt_search_query);
				locationSearchBox = (AutoCompleteTextView) searchBoxView
						.findViewById(R.id.txt_search_district);
				locationSearchBox.setAdapter(adapter);

				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Search",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										//String searchQuery = searchBox.getText().toString();
										//String locationValue = locationSearchBox.getText().toString();

									//	String query = searchQuery + " " + locationValue;
										
										//List<>
										
										//Global search should only take u to the listing file
										
										if (Globals.getInstance() == null) {
											Globals.getInstance();
										}
										HashMap<String, List<Buddy>> datas = Globals
												.getDataz();

										if (!datas.isEmpty()) {
											for (List<Buddy> buddies : datas
													.values()) {
												for (@SuppressWarnings("unused") Buddy buddy : buddies) {
													
												}
											}
										}
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

	@SuppressWarnings("deprecation")
	@Override
	public void onBackPressed() {
		AlertDialog alert_back = new AlertDialog.Builder(this).create();
		alert_back.setTitle("UG Buddy");
		alert_back.setMessage("Are you sure want to Quit?");

		alert_back.setButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alert_back.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Intent intent = new Intent(Intent.ACTION_MAIN);
				Intent intent = new Intent(getApplicationContext(),
						SplashScreen.class);
				// intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("EXIT", true);
				startActivity(intent);
			}
		});
		alert_back.show();
	}
}
