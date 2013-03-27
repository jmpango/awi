package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.awi.ui.model.Buddy;
import org.awi.ui.server.service.AlertPositiveListener;
import org.awi.ui.server.service.BackHomeButtonListener;
import org.awi.ui.server.util.BuddyContants;
import org.awi.ui.server.util.BuddyDialogRadio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Details extends BaseActivity implements TextWatcher,
		AlertPositiveListener, BackHomeButtonListener {

	private Buddy buddy;
	private TextView details_name;
	private TextView details_tagline;
	private TextView details_address;
	private TextView details_tel;
	private TextView details_email;
	private TextView details_website;
	private ImageButton website_btn, call_btn, email_btn, rate_btn;
	private String[] tels;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_layout);
		this.buddy = (Buddy) getIntent().getParcelableExtra(BuddyContants.DEFAULT_BUDDY);
		
//		/getSerializableExtra();

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
		this.locationSearchBox = (AutoCompleteTextView) findViewById(R.id.txt_search_query);
		
		callBtnClickHandler();
		ratemeBtnClickHandler();
		backHomeBtnClickHandler();

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
				BuddyDialogRadio alert = new BuddyDialogRadio(tels,
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
		startActivity(callIntent);
	}

	private void gabaggeCollector() {
		tels = null;
	}

	@Override
	public void backHomeBtnClickHandler() {
		homeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Details.this, MainBuddy.class);
				gabaggeCollector();
				startActivity(intent);
			}
		});
	}

	@Override
	public void clossApplicationBtnClickHandler() {

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

}
