package org.awi.ui;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import android.content.res.AssetManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity implements BaseBuddy, OnSearchListener{

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public TextView msgLabel;
	public EditText dashBoardSearchBox;
	public final String KEY_NAME = "name";
	public ProgressDialog pDialog;
	public ImageButton homeBtn;
	public BaseActivity baseActivity;
	public BuddyService buddyService;
	
	public BaseActivity() {
		this.baseActivity = this;
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
			Document document = docBuilder.parse(asserManager.open(xmlFileName));
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
					names[i] = ((Node) _name.item(0)).getNodeValue().trim();;
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
	public void search(List<?> collection) {
		
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
				onDestroy();
			}
		});
		alert_back.show();
	}
}
