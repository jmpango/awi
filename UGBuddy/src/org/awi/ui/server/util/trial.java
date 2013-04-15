package org.awi.ui.server.util;





public class trial {
	private String[]data;
	public trial() {
		data = new String[]{};
		data[0] = "";
	}
	
	public static void main(String[] args){
	/*	int m = 32;
		int l = 32;
		int h = 48;*/
	}
}
/*
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
				Intent intent = new Intent(getApplicationContext(),
						SplashScreenUI.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("EXIT", true);
				startActivity(intent);
			}
		});
		alert_back.show();
	}
	
	public void baseActivityGabaggeCollector(){
		this.msgLabel = null;
		this.dashBoardSearchBox =null;
		this.searchBox = null ;
		this.pDialog = null;
		this.homeBtn = null;
		this.advancedSearchBtn = null;
		this.buddyService = null;
		this.appListView = null;
		try {
			BaseActivity.this.finalize();
		} catch (Throwable e) {
		}
	}





----------------------------------------------------------------------------
extends BaseActivity implements OnItemClickListener,
TextWatcher, AlertPositiveListener, BackHomeButtonListener {

private ListView dashBoardListView;
private DashboardAsyncTask dashboardAsyncTask;
private DashboardSearchAsyncTask dashboardSearchAsyncTask;
private DashboardService dashBoardService;
private String[] popUpNameListing;
private List<DashBoard> dashBoardList, tempDashboardList;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main_buddy_layout);
prepareUI();
}

private void prepareUI() {
hideMsgBox();

this.dashBoardListView = (ListView) findViewById(R.id.dashboard_list_view);
this.dashBoardSearchBox = (EditText) findViewById(R.id.search_box);
this.homeBtn = (ImageButton) findViewById(R.id.home_btn);
this.advancedSearchBtn = (ImageButton) findViewById(R.id.advanced_search_btn);
this.searchBox = (EditText) findViewById(R.id.txt_search_query);

backHomeBtnClickHandler();

this.dashBoardSearchBox.addTextChangedListener(this);
this.dashBoardListView.setOnItemClickListener(this);

this.dashboardAsyncTask = new DashboardAsyncTask();
dashboardAsyncTask.execute();

advancedSearch();
}

private class DashboardAsyncTask extends AsyncTask<Void, Void, Void> {

@Override
protected void onPreExecute() {
	showProgressBar(DashboardUI.this);
}

@Override
protected void onPostExecute(Void result) {
	tempDashboardList = /* dashBoardService.getDashBoardData() null;
	hideProgressBar();
	if (dashBoardList == null)
		dashBoardList = tempDashboardList;

	dashBoardListView.setAdapter(new DashboardDataBinder(
			DashboardUI.this, tempDashboardList));
}

@Override
protected Void doInBackground(Void... params) {
	tempDashboardList = new ArrayList<DashBoard>();
	if (dashBoardService == null) {
		dashBoardService = /*
							 * DashboardServiceImpl.getInstance(getAssets
							 * ())
							 null;
	}
	return null;
}
}



@Override
public void onPositiveClick(int position) {
if (popUpNameListing != null) {
	Globals.getInstance();
	String name = BuddyContants.REMOVE_WHITESPACES(
			popUpNameListing[position]).toLowerCase();
	List<Buddy> buddies = Globals.getBuddy(name);
	if (buddies.size() == 0) {
		BuddyContants.LOAD_APP_DATA(getAssets(),
				BuddyServiceImpl.getInstance());
		buddies = Globals.getBuddy(name);
	}

	Intent intent = new Intent(DashboardUI.this, Listing.class);
	Bundle bundle = new Bundle();
	bundle.putString(BuddyContants.PAGE_NAME,
			popUpNameListing[position]);
	bundle.putParcelableArrayList(BuddyContants.BUDDY_LISTING,
			(ArrayList<? extends Parcelable>) buddies);
	intent.putExtras(bundle);
	gabaggeCollector();
	startActivity(intent);

}
}

private void gabaggeCollector() {
try {
	this.dashBoardListView = null;

	if (dashboardAsyncTask != null) {
		dashboardAsyncTask.cancel(true);
		dashboardAsyncTask = null;
	}

	if (dashboardSearchAsyncTask != null) {
		dashboardSearchAsyncTask.cancel(true);
		dashboardSearchAsyncTask = null;
	}

	dashBoardService = null;
	popUpNameListing = null;
	dashBoardList.clear();
	tempDashboardList.clear();
	dashBoardList = null;
	tempDashboardList = null;
	msgLabel = null;
	dashBoardSearchBox = null;
	pDialog = null;
	homeBtn = null;
	DashboardUI.this.finalize();
} catch (Throwable e) {
}
}

@Override
public void afterTextChanged(Editable arg0) {

}

@Override
public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
	int arg3) {

}

@Override
public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
this.dashboardSearchAsyncTask = new DashboardSearchAsyncTask();
dashboardSearchAsyncTask.execute();

}

@Override
public void onItemClick(AdapterView<?> parent, View view, int position,
	long id) {
DashBoard dBoard = tempDashboardList.get(position);
String selectItem = BuddyContants.REMOVE_WHITESPACES(dBoard.getName())
		.toLowerCase();

popUpNameListing = getData(getAssets(), BuddyContants.LISTING,
		selectItem + "/" + selectItem + ".xml");

FragmentManager manager = getSupportFragmentManager();
BuddyDialogRadio alert = new BuddyDialogRadio(popUpNameListing,
		dBoard.getName() + " Listing", "view");

Bundle bundle = new Bundle();
bundle.putInt("position", position);
alert.setArguments(bundle);
alert.show(manager, "alert_dialog_radio");
}

@Override
public void backHomeBtnClickHandler() {
homeBtn.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		Toast.makeText(DashboardUI.this, "Already Home",
				Toast.LENGTH_SHORT).show();
	}
});
}

@Override
public void clossApplicationBtnClickHandler() {

}

public void advancedSearch() {
advancedSearchBtn.setOnClickListener(new OnClickListener() {

	@Override
	public void onClick(View v) {
		LayoutInflater layoutInflater = LayoutInflater
				.from(DashboardUI.this);
		View searchBoxView = layoutInflater.inflate(
				R.layout.advanced_search_layout, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				DashboardUI.this);
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
								String searchQuery = searchBox
										.getText().toString()
										.toLowerCase();
								List<Buddy> searchList = new ArrayList<Buddy>();

								if (Globals.getInstance() == null) {
									Globals.getInstance();
								}
								HashMap<String, List<Buddy>> datas = Globals
										.getDataz();

								if (!datas.isEmpty()) {
									for (List<Buddy> buddies : datas
											.values()) {
										for (Buddy buddy : buddies) {

											// -------- BY Buddy Name
											// ---------//
											String searchByName = buddy
													.getName()
													.toLowerCase();
											if (searchByName
													.contains(searchQuery)) {
												if (!searchList
														.contains(buddy)) {
													searchList
															.add(buddy);
													// break;
												}
											} else {
												if (isExisting(
														searchByName,
														searchQuery)) {
													if (!searchList
															.contains(buddy)) {
														searchList
																.add(buddy);
														// break;
													}
												} else {
													boolean isAvailable = false;
													for (Location loc : buddy
															.getLocations()) {
														String locationName = loc
																.getName()
																.toLowerCase();
														if (locationName
																.contains(searchQuery)) {
															if (!searchList
																	.contains(buddy)) {
																searchList
																		.add(buddy);
																isAvailable = true;
																// break;
															}
														} else {
															if (isExisting(
																	locationName,
																	searchQuery)) {
																if (!searchList
																		.contains(buddy)) {
																	searchList
																			.add(buddy);
																	isAvailable = true;
																	// break;
																}
															}
														}
													}

													if (!isAvailable) {
														for (SearchTag sTag : buddy
																.getSearchTags()) {
															String locationName = sTag
																	.getName()
																	.toLowerCase();
															if (locationName
																	.contains(searchQuery)) {
																if (!searchList
																		.contains(buddy)) {
																	searchList
																			.add(buddy);
																	// break;
																}
															} else {
																if (isExisting(
																		locationName,
																		searchQuery)) {
																	if (!searchList
																			.contains(buddy)) {
																		searchList
																				.add(buddy);
																		// break;
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}

								if (searchList.isEmpty())
									showMsgBox(searchQuery
											+ " has no record. Try another search term.");
								else {
									Intent intent = new Intent(
											getApplication(),
											Listing.class);
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
									startActivity(intent);
								}
							}

							private boolean isExisting(
									String searchWord,
									String searchQuery) {
								String[] splitDemilter = searchQuery
										.split("\\s+");
								for (int index = 0; index < splitDemilter.length; index++) {
									if (searchWord
											.contains(splitDemilter[index])) {
										return true;
									}
								}
								return false;
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
Toast.makeText(DashboardUI.this, msg, Toast.LENGTH_SHORT).show();
}

public boolean onCreateOptionsMenu(Menu menu) {
getMenuInflater().inflate(R.menu.menu_layout, menu);
return true;
}

@Override
public boolean onContextItemSelected(MenuItem item) {
switch (item.getItemId()) {
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
default:
	return super.onOptionsItemSelected(item);
}
}
==========================================================



*/




















/*splashThread = new Thread() {
//long seconds = 0;

public void run() {
	try {
		while (isRuning && seconds < 3000) {
			seconds += 100;

			if (seconds == 2999) {
				isRuning = false;
				sleep(100);
			}
		}
	} catch (Exception e) {
	
	} finally {
		
	}
}
};

splashThread.start();*/

/*
 * startActivityForResult
 * http://www.visituganda.com/directory/?jc=tour&page=1
 * 
 * button.setOnClickListener(new OnClickListener() {
	 
	@Override
	public void onClick(View arg0) {

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:0377778888"));
		startActivity(callIntent);

	}

});*/

/*@Override
public void onItemClick(AdapterView<?> a,
        View v, int position, long id) {
    City city = (City) a.getItemAtPosition(position);
    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
    intent.putExtra("com.example.cities.City", city);
    startActivity(intent);
}
});

In your details activity, get the city from the extras on the intent:

Bundle bundle = getIntent().getExtras();
City city = bundle.getParcelable("com.example.cities.City");*/

/*private long _seconds = 0;
private long _splashTime = 5000;
private boolean _isSplashActive = true;
private boolean _splashPaused = false;
private Thread _splashThread;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//setContentView(R.layout.splash_view);

	_splashThread = new Thread() {
		public void run() {
			try {
				while (_isSplashActive && _seconds < _splashTime) {
					if (!_splashPaused)
						_seconds += 100;
					sleep(100);
				}
			} catch (Exception e) {
			} finally {
				Intent intent = new Intent(_splash.this, Dashboard.class);
				startActivity(intent);
			}
		}
	};
	
	_splashThread.start();
}*/


/*String uri = "drawable/rating_ico";
int imageResource = vi
		.getContext()
		.getApplicationContext()
		.getResources()
		.getIdentifier(
				uri,
				null,
				vi.getContext().getApplicationContext()
						.getPackageName());
Drawable image = vi.getContext().getResources()
		.getDrawable(imageResource);
holder.image.setImageDrawable(image);*/

/*PhoneCallListener phoneListener = new PhoneCallListener();
TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);


private class PhoneCallListener extends PhoneStateListener {
		 
		private boolean isPhoneCalling = false;
		public void onCallStateChanged(int state, String incomingNumber) {
 
			if (TelephonyManager.CALL_STATE_RINGING == state) {
			}
 
			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				isPhoneCalling = true;
			}
 
			if (TelephonyManager.CALL_STATE_IDLE == state) {
				if (isPhoneCalling) {
					/*Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(
							getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
 
					isPhoneCalling = false;
				}
 
			}
		}
	}*/

	
