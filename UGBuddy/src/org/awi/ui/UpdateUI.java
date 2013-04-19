package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.awi.ui.model.Buddy;
import org.awi.ui.model.BuddyLocation;
import org.awi.ui.model.BuddySearchTag;
import org.awi.ui.model.DashBoard;
import org.awi.ui.model.DashboardCategory;
import org.awi.ui.model.Usage;
import org.awi.ui.server.service.BuddyService;
import org.awi.ui.server.service.DashboardCategoryService;
import org.awi.ui.server.service.DashboardService;
import org.awi.ui.server.service.UsageService;
import org.awi.ui.server.service.impl.BuddyServiceImpl;
import org.awi.ui.server.service.impl.DashboardCategoryServiceImpl;
import org.awi.ui.server.service.impl.DashboardServiceImpl;
import org.awi.ui.server.service.impl.UsageServiceImpl;
import org.awi.ui.server.util.BuddyContants;
import org.awi.ui.server.util.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UpdateUI extends BaseActivity {
	private TextView tv_progress, loadingTxtView;
	private ProgressBar pb_progressBar;
	private Button startDownloadBtn;
	private DashboardService dashboardService;
	private BuddyService buddyService;
	private UsageService usageService;
	private DashboardCategoryService dashboardCategoryService;
	private int incremental = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateui_layout);

		tv_progress = (TextView) findViewById(R.id.tv_progress);
		pb_progressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
		startDownloadBtn = (Button) findViewById(R.id.downloadBtn);
		loadingTxtView = (TextView) findViewById(R.id.loadingtext);
		loadingTxtView.setVisibility(View.GONE);

		dashboardService = new DashboardServiceImpl(this);
		dashboardCategoryService = new DashboardCategoryServiceImpl(this);

		startDownloadBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isNetworkAvailable())
					new DownloadTaskHandler().execute();
				else {
					showToast(UpdateUI.this,
							"No network connection ..Reconnect and Try Again. ");
				}
			}
		});

	}

	private boolean isNetworkAvailable() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] activeNetworkInfo = connectivityManager
				.getAllNetworkInfo();

		for (NetworkInfo ni : activeNetworkInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}

		return haveConnectedWifi || haveConnectedMobile;
	}

	private class DownloadTaskHandler extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... args) {
			submitUsage();
			downloadBuddySearchTags();
			downloadBuddyLocations();
			downloadBuddies();
			downloadDashboardCategory();
			downloadDashboard();
			return null;
		}

		private void submitUsage() {
			usageService = new UsageServiceImpl(UpdateUI.this);
			List<Usage> usages = usageService.getAllUsage();
			incremental = 0;
			incremental += 5;
			publishProgress(incremental);
			if (!usages.isEmpty()) {
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();

					JSONObject jsonObj = new JSONObject();
					JSONArray jsonArray = new JSONArray();
					for (Usage usage : usages) {
						JSONObject childObj = new JSONObject();
						childObj.put("pageHit", usage.getPageHits() + "");
						childObj.put("callHit", usage.getCallHits() + "");
						childObj.put("urlHit", usage.getUrlHits() + "");
						childObj.put("emailHit", usage.getEmailHits() + "");
						childObj.put("buddyId", usage.getBuddyId() + "");

						jsonArray.put(childObj);
					}
					jsonObj.put("usages", jsonArray);
					params.add(new BasicNameValuePair("hits", jsonObj
							.toString()));

					JSONParser jsonParser = new JSONParser();
					jsonParser.makeHttpRequest(BuddyContants.SERVER_URL
							+ "usage", "POST", params);
					incremental += 10;
					publishProgress(incremental);
					usageService.clearUsage();
					incremental += 1;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		private void downloadBuddySearchTags() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONParser jsonParser = new JSONParser();

			incremental += 6;
			
			String updateDate = buddyService.getBuddySearchTagUpdateDate();
			String[] split = updateDate.split("\\s+");
			updateDate = split[0];

			JSONObject jsonObject = jsonParser.makeHttpRequest(
					BuddyContants.SERVER_URL + "buddysearchtag/" + updateDate,
					"GET", params);

			incremental += 5;
			publishProgress(incremental);
			try {
				int sucess = jsonObject.getInt("sucess");
				if (sucess == 1) {
					String nextUpdateDate = jsonObject
							.getString("lastupdatedate");

					JSONArray buddySearchArray = jsonObject
							.getJSONArray("buddysearchtags");

					for (int i = 0; i < buddySearchArray.length(); i++) {
						JSONObject jsonBuddySearchtag = buddySearchArray
								.getJSONObject(i);
						BuddySearchTag buddySearchTag = new BuddySearchTag(
								jsonBuddySearchtag.getInt("id"),
								jsonBuddySearchtag.getString("search_value"),
								jsonBuddySearchtag.getString("buddy_id"));

						if (buddyService.getBuddySearchTagById(buddySearchTag
								.getId()) != null) {
							buddyService.updateBuddySearchTag(buddySearchTag);
						} else {
							buddyService.addBuddySearchTag(buddySearchTag);
						}

					}

					JSONArray buddySearchTagDeleteArray = jsonObject
							.getJSONArray("deleteBuddysearchtags");
					for (int i = 0; i < buddySearchTagDeleteArray.length(); i++) {
						JSONObject jsonDeleteBuddySearchTag = buddySearchTagDeleteArray
								.getJSONObject(i);
						BuddySearchTag buddySearchTag = new BuddySearchTag();
						buddySearchTag.setId(jsonDeleteBuddySearchTag
								.getInt("id"));

						buddyService.deleteBuddySearchTag(buddySearchTag);
					}
					buddyService.setBuddySearchTagUpdateDate(nextUpdateDate);
				}

				incremental += 5;
				publishProgress(incremental);
			} catch (Exception e) {
				Log.e("UBUddyy Error", e.getMessage());
			}
		}

		private void downloadBuddyLocations() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONParser jsonParser = new JSONParser();

			incremental += 6;
			publishProgress(incremental);

			String updateDate = buddyService.getBuddyLocationUpdateDate();
			String[] split = updateDate.split("\\s+");
			updateDate = split[0];

			JSONObject jsonObject = jsonParser.makeHttpRequest(
					BuddyContants.SERVER_URL + "buddylocations/" + updateDate,
					"GET", params);

			incremental += 5;
			publishProgress(incremental);
			try {
				int sucess = jsonObject.getInt("sucess");
				if (sucess == 1) {
					String nextUpdateDate = jsonObject
							.getString("lastupdatedate");

					JSONArray buddyLocationArray = jsonObject
							.getJSONArray("buddylocations");

					for (int i = 0; i < buddyLocationArray.length(); i++) {
						JSONObject jsonBuddyLocation = buddyLocationArray
								.getJSONObject(i);
						BuddyLocation buddyLocation = new BuddyLocation(
								jsonBuddyLocation.getInt("id"),
								jsonBuddyLocation.getString("location_name"),
								jsonBuddyLocation.getString("buddy_id"));

						if (buddyService.getBuddyLocationById(buddyLocation
								.getId()) != null) {
							buddyService.updateBuddyLocation(buddyLocation);
						} else {
							buddyService.addBuddyLocation(buddyLocation);
						}

					}

					JSONArray buddyLocationDeleteArray = jsonObject
							.getJSONArray("deleteBuddylocations");
					for (int i = 0; i < buddyLocationDeleteArray.length(); i++) {
						JSONObject jsonDeleteBuddyLocation = buddyLocationDeleteArray
								.getJSONObject(i);
						BuddyLocation buddyLocation = new BuddyLocation();
						buddyLocation.setId(jsonDeleteBuddyLocation
								.getInt("id"));

						buddyService.deleteBuddyLocation(buddyLocation);
					}
					buddyService.setBuddyLocationUpdateDate(nextUpdateDate);
				}

				incremental += 6;
				publishProgress(incremental);
			} catch (Exception e) {
				Log.e("UBUddyy Error", e.getMessage());
			}
		}

		private void downloadBuddies() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONParser jsonParser = new JSONParser();

			incremental += 6;
			publishProgress(incremental);

			String updateDate = buddyService.getUpdateDate();
			String[] split = updateDate.split("\\s+");
			updateDate = split[0];

			JSONObject jsonObject = jsonParser.makeHttpRequest(
					BuddyContants.SERVER_URL + "buddies/" + updateDate, "GET",
					params);

			incremental += 5;
			publishProgress(incremental);
			try {
				int sucess = jsonObject.getInt("sucess");
				if (sucess == 1) {
					String nextUpdateDate = jsonObject
							.getString("lastupdatedate");

					JSONArray buddyArray = jsonObject.getJSONArray("buddies");

					for (int i = 0; i < buddyArray.length(); i++) {
						JSONObject jsonBuddyCategory = buddyArray
								.getJSONObject(i);
						Buddy buddy = new Buddy(jsonBuddyCategory.getInt("id"),
								jsonBuddyCategory.getString("name"),
								jsonBuddyCategory.getString("tagline"),
								jsonBuddyCategory.getString("email"),
								jsonBuddyCategory.getString("telphone"),
								jsonBuddyCategory.getString("url"),
								jsonBuddyCategory.getString("fax"),
								jsonBuddyCategory.getString("address"),
								jsonBuddyCategory
										.getString("dashboard_category_id"));

						if (buddyService.getBuddyById(buddy.getId()) != null) {
							buddyService.updateBuddy(buddy);
						} else {
							buddyService.addBuddy(buddy);
						}

					}

					JSONArray buddyDeleteArray = jsonObject
							.getJSONArray("deleteBuddies");
					for (int i = 0; i < buddyDeleteArray.length(); i++) {
						JSONObject jsonDeleteBuddy = buddyDeleteArray
								.getJSONObject(i);
						Buddy buddy = new Buddy();
						buddy.setId(jsonDeleteBuddy.getInt("id"));

						buddyService.deleteBuddy(buddy);
					}
					buddyService.setUpdateDate(nextUpdateDate);
				}

				incremental += 6;
				publishProgress(incremental);
			} catch (Exception e) {
				Log.e("UBUddyy Error", e.getMessage());
			}
		}

		private void downloadDashboardCategory() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONParser jsonParser = new JSONParser();

			incremental += 6;
			publishProgress(incremental);

			String updateDate = dashboardCategoryService.getUpdateDate();
			String[] split = updateDate.split("\\s+");
			updateDate = split[0];

			JSONObject jsonObject = jsonParser.makeHttpRequest(
					BuddyContants.SERVER_URL + "dashboardcategory/"
							+ updateDate, "GET", params);

			incremental += 5;
			publishProgress(incremental);
			try {
				int sucess = jsonObject.getInt("sucess");
				if (sucess == 1) {
					String nextUpdateDate = jsonObject
							.getString("lastupdatedate");

					JSONArray dashboardCategoryArray = jsonObject
							.getJSONArray("dashboardCategories");

					for (int i = 0; i < dashboardCategoryArray.length(); i++) {
						JSONObject jsonDashboardCategory = dashboardCategoryArray
								.getJSONObject(i);
						DashboardCategory dashboardCategory = new DashboardCategory(
								jsonDashboardCategory.getInt("id"),
								jsonDashboardCategory.getString("cname"),
								jsonDashboardCategory.getString("dashboard_id"));

						if (dashboardCategoryService
								.getDashboardCategoryById(dashboardCategory
										.getId()) != null) {
							dashboardCategoryService
									.updateDashboardCategory(dashboardCategory);
						} else {
							dashboardCategoryService
									.addDashboardCategory(dashboardCategory);
						}

					}

					JSONArray dashboardCatDeleteArray = jsonObject
							.getJSONArray("deleteDashboardCategory");
					for (int i = 0; i < dashboardCatDeleteArray.length(); i++) {
						JSONObject jsonDeleteDashboardCat = dashboardCatDeleteArray
								.getJSONObject(i);
						DashboardCategory dashboardCategory = new DashboardCategory(
								jsonDeleteDashboardCat.getInt("id"), "", "");

						dashboardCategoryService
								.deleteDashboardCategory(dashboardCategory);
					}

					dashboardCategoryService.setUpdateDate(nextUpdateDate);
				}

				incremental += 6;
				publishProgress(incremental);
			} catch (Exception e) {
				Log.e("UBUddyy Error", e.getMessage());
			}
		}

		private void downloadDashboard() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONParser jsonParser = new JSONParser();
			incremental = +6;
			publishProgress(incremental);

			String updateDate = dashboardService.getUpdateDate();
			String[] split = updateDate.split("\\s+");
			updateDate = split[0];

			JSONObject jsonObject = jsonParser.makeHttpRequest(
					BuddyContants.SERVER_URL + "dashboard/" + updateDate,
					"GET", params);

			incremental += 5;
			publishProgress(incremental);
			Log.d("Downloading Dashboard data..", jsonObject.toString());
			try {
				int sucess = jsonObject.getInt("sucess");
				if (sucess == 1) {
					String nextUpdateDate = jsonObject
							.getString("lastupdatedate");

					JSONArray dashboardArray = jsonObject
							.getJSONArray("dashboards");

					for (int i = 0; i < dashboardArray.length(); i++) {
						JSONObject jsonDashboard = dashboardArray
								.getJSONObject(i);
						DashBoard dashboard = new DashBoard(
								jsonDashboard.getInt("id"),
								jsonDashboard.getString("dname"),
								jsonDashboard.getString("tagline"));

						if (dashboardService
								.getDashboardById(dashboard.getId()) != null) {
							dashboardService.updateDashboard(dashboard);
						} else {
							dashboardService.addDashboard(dashboard);
						}

					}

					JSONArray dashboardDeleteArray = jsonObject
							.getJSONArray("deleteDashboard");
					for (int i = 0; i < dashboardDeleteArray.length(); i++) {
						JSONObject jsonDeleteDashboard = dashboardDeleteArray
								.getJSONObject(i);
						DashBoard dashboard = new DashBoard(
								jsonDeleteDashboard.getInt("id"), "", "");

						dashboardService.deleteDashboard(dashboard);
					}

					dashboardService.setUpdateDate(nextUpdateDate);
				}

				incremental += 6;
				publishProgress(incremental);
			} catch (Exception e) {
				Log.e("UBUddyy Error", e.getMessage());
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			Intent intent = new Intent(UpdateUI.this, DashboardUI.class);
			UpdateUI.this.finish();
			startActivity(intent);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			pb_progressBar.setMax(100);
			loadingTxtView.setVisibility(View.VISIBLE);
			buddyService = new BuddyServiceImpl(UpdateUI.this);
			super.onPreExecute();
		}

		protected void onProgressUpdate(Integer... values) {
			if (values[0] <= 100) {
				tv_progress.setText("Progress: " + Integer.toString(values[0])
						+ "%");
				pb_progressBar.setProgress(values[0]);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
