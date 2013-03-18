package org.awi.ui;

import org.awi.ui.model.Buddy;
import org.awi.ui.server.util.BuddyContants;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Details extends BaseActivity implements  TextWatcher  {

	private Buddy buddy;
	private TextView details_name;
	private TextView details_tagline;
	private TextView details_address;
	private TextView details_tel;
	private TextView details_email;
	private TextView details_website;
	private ImageButton visit_website_btn;
	private TextView pageTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_layout);
		this.buddy = (Buddy) getIntent().getSerializableExtra(BuddyContants.DEFAULT_BUDDY);
		
		buildUI();
		hideMsgBox();
		
	}

	private void buildUI() {
		LinearLayout search_dv = (LinearLayout) findViewById(R.id.search_dv);
		search_dv.setVisibility(View.GONE);
		
		pageTitle = (TextView) findViewById(R.id.pageTitle);
		pageTitle.setVisibility(View.GONE);
		
		details_name = (TextView) findViewById(R.id.details_name);
		details_address = (TextView) findViewById(R.id.details_address_text);
		details_tagline = (TextView) findViewById(R.id.details_tagline);
		details_tel = (TextView) findViewById(R.id.details_tel_text);
		details_email = (TextView) findViewById(R.id.details_email_text);
		details_website = (TextView) findViewById(R.id.details_website_text);
		visit_website_btn = (ImageButton) findViewById(R.id.visit_website_btn);
		
		details_name.setText(buddy.getName());
		details_tagline.setText(buddy.getTagLine());
		details_address.setText(buddy.getAddress());
		details_tel.setText(buddy.getTelphoneNos());
		
		if(buddy.getEmail() == null){
			
		}else{
			details_email.setText(buddy.getEmail());
		}
		
		if(buddy.getUrl() == null){
			visit_website_btn.setVisibility(View.GONE);
		}else{
			visit_website_btn.setVisibility(View.VISIBLE);
			details_website.setText(buddy.getUrl());
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

	/*@SuppressWarnings("deprecation")
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
				try {
					details.finalize();
				} catch (Throwable e) {
				}
			}
		});
		alert_back.show();
	}*/
}
