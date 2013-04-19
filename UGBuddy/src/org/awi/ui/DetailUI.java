package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.server.service.AlertPositiveListener;
import org.awi.ui.server.service.UsageService;
import org.awi.ui.server.service.impl.UsageServiceImpl;
import org.awi.ui.server.util.BuddyContants;
import org.awi.ui.server.util.RadioDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailUI extends BaseActivity implements TextWatcher,
		AlertPositiveListener {

	private Buddy buddy;
	private TextView details_name;
	private TextView details_tagline;
	private TextView details_address;
	private TextView details_tel;
	private TextView details_email;
	private TextView details_website;
	private ImageButton website_btn, call_btn, email_btn, rate_btn;
	private String[] tels;
	private UsageService usageService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailui_layout);
		this.buddy = (Buddy) getIntent().getParcelableExtra(
				BuddyContants.DEFAULT_BUDDY);
		
		usageService = new UsageServiceImpl(this);
		usageService.savePageHit(buddy.getId());
		
		buildUI();
		hideMsgBox();
		advancedSearch();
	}

	private void buildUI() {
		LinearLayout search_dv = (LinearLayout) findViewById(R.id.search_dv);
		search_dv.setVisibility(View.GONE);

		details_name = (TextView) findViewById(R.id.details_name);
		details_address = (TextView) findViewById(R.id.details_address_text);
		details_tagline = (TextView) findViewById(R.id.details_tagline);
		details_tel = (TextView) findViewById(R.id.details_tel_text);
		details_email = (TextView) findViewById(R.id.details_email_text);
		details_website = (TextView) findViewById(R.id.details_website_text);
		website_btn = (ImageButton) findViewById(R.id.visit_website_btn);

		call_btn = (ImageButton) findViewById(R.id.details_call_btn);
		email_btn = (ImageButton) findViewById(R.id.details_email_btn);
		rate_btn = (ImageButton) findViewById(R.id.rateme_btn);
		homeBtn = (ImageButton) findViewById(R.id.home_btn);
		this.advancedSearchBtn = (ImageButton) findViewById(R.id.advanced_search_btn);
		this.searchBox = (EditText) findViewById(R.id.txt_search_query);

		callBtnClickHandler();
		ratemeBtnClickHandler();

		details_name.setText(buddy.getName());
		details_tagline.setText(buddy.getTagLine());
		details_address.setText(buddy.getAddress());
		details_tel.setText(buddy.getTelphoneNos());

		if (buddy.getEmail() == null) {
			email_btn.setVisibility(View.GONE);
		} else {
			email_btn.setVisibility(View.VISIBLE);
			details_email.setText(buddy.getEmail());
			emailBtnClickHandler();
		}

		if (buddy.getUrl() == null) {
			website_btn.setVisibility(View.GONE);
		} else {
			website_btn.setVisibility(View.VISIBLE);
			details_website.setText(buddy.getUrl());
			websiteBtnClickHandler();
		}

		String telNos = buddy.getTelphoneNos() + ",";
		String extractNmbrz = telNos.substring(telNos.indexOf(")") + 1,
				telNos.length()).trim();
		List<String> nos = new ArrayList<String>();

		int startChar = 0;
		int endChar = -1;

		for (int index = 0; index < extractNmbrz.length(); index++) {
			if (extractNmbrz.charAt(index) == ',') {
				endChar = index - 1;
				nos.add("+256"
						+ extractNmbrz.substring(startChar, endChar + 1).trim());
				startChar = index + 1;
			}
		}

		tels = new String[nos.size()];
		for (int incremental = 0; incremental < nos.size(); incremental++) {
			tels[incremental] = nos.get(incremental);
		}
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

	}

	private void callBtnClickHandler() {
		call_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager manager = getSupportFragmentManager();
				RadioDialog alert = new RadioDialog(tels,
						"Select a Number", "Call");
				Bundle bundle = new Bundle();
				bundle.putInt("position", 0);
				alert.setArguments(bundle);
				alert.show(manager, "alert_dialog_radio");
			}
		});
	}

	private void websiteBtnClickHandler() {
		website_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://" + buddy.getUrl()));
				usageService.saveUrlHit(buddy.getId());
				startActivity(browserIntent);
			}
		});
	}

	private void ratemeBtnClickHandler() {
		rate_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	private void emailBtnClickHandler() {
		email_btn.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(1);
			}
		});
	}

	@Override
	public void onPositiveClick(int position) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + tels[position]));
		usageService.saveCallHit(buddy.getId());
		startActivity(callIntent);
	}

	protected Dialog onCreateDialog(int id) {

		AlertDialog dialogDetails = null;

		switch (id) {
		case 1:
			LayoutInflater inflater = LayoutInflater.from(this);
			View dialogview = inflater.inflate(R.layout.email_layout, null);

			AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
			dialogbuilder.setTitle("Email: " + buddy.getName());
			dialogbuilder.setView(dialogview);
			dialogDetails = dialogbuilder.create();

			break;
		}

		return dialogDetails;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		switch (id) {
		case 1:
			final AlertDialog alertDialog = (AlertDialog) dialog;
			Button sendbutton = (Button) alertDialog
					.findViewById(R.id.email_btn_send);
			Button cancelbutton = (Button) alertDialog
					.findViewById(R.id.email_btn_cancel);
			final EditText emailSubject = (EditText) alertDialog
					.findViewById(R.id.txtemail_subject);
			final EditText emailMessage = (EditText) alertDialog
					.findViewById(R.id.txtemail_message);
			sendbutton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String subject = emailSubject.getText().toString();
					String message = emailMessage.getText().toString();
					String to = buddy.getEmail().trim();

					Intent email = new Intent(Intent.ACTION_SEND);
					email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
					email.putExtra(Intent.EXTRA_SUBJECT, subject);
					email.putExtra(Intent.EXTRA_TEXT, message);

					email.setType("message/rfc822");
					try {
						usageService.saveEmailHit(buddy.getId());
						startActivity(Intent.createChooser(email,
								"Choose an Email client :"));
					} catch (android.content.ActivityNotFoundException ex) {
						showToast("There are no email clients installed.");
					}

					alertDialog.dismiss();
					showToast("Email sent to " + buddy.getEmail());
				}
			});

			cancelbutton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					alertDialog.dismiss();
				}
			});
			break;
		}
	}

	public void advancedSearch() {
		advancedSearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(DetailUI.this);
				View searchBoxView = layoutInflater.inflate(
						R.layout.advanced_search_layout, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						DetailUI.this);
				alertDialogBuilder.setView(searchBoxView);

				searchBox = (EditText) searchBoxView
						.findViewById(R.id.txt_search_query);

				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Search",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										/*String searchQuery = searchBox
												.getText().toString()
												.toLowerCase();
										List<Buddy> searchList = new ArrayList<Buddy>();

										if (Globals.getInstance() == null) {
											Globals.getInstance();
										}
										HashMap<String, List<Buddy>> datas = Globals.getDataz() null;

											Intent intent = new Intent(
													getApplication(),
													ListingUI.class);
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
											startActivity(intent);*/
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
		Toast.makeText(DetailUI.this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.about_ug:
			showToast("About Uganda is Selected");
			return false;
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
