package org.awi.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.awi.ui.model.DashBoard;
import org.awi.ui.model.DashboardCategory;
import org.awi.ui.server.service.DashboardCategoryService;
import org.awi.ui.server.service.DashboardService;
import org.awi.ui.server.service.impl.DashboardCategoryServiceImpl;
import org.awi.ui.server.service.impl.DashboardServiceImpl;
import org.awi.ui.server.util.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
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
	private String serverURL = "http://10.0.2.2:8000/w-awi/xxx/mobileApi/list/";
	private Button startDownloadBtn;
	private DashboardService dashboardService;
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
				new DownloadTaskHandler().execute();
			}
		});

	}

	private class DownloadTaskHandler extends AsyncTask<Void, Integer, Void> {
		@Override
		protected Void doInBackground(Void... args) {
			downloadDashboardCategory();
			downloadDashboard();
			return null;
		}

		private void downloadDashboardCategory() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONParser jsonParser = new JSONParser();
		
			incremental += 15;
			publishProgress(incremental);
			
			String updateDate = dashboardCategoryService.getUpdateDate();
			String[] split= updateDate.split("\\s+");
			updateDate = split[0];
			
			Log.d("update date ..", updateDate);
			JSONObject jsonObject = jsonParser.makeHttpRequest(serverURL+"dashboardcategory/"+updateDate, "GET", params);
			
			incremental += 20;
			publishProgress(incremental);
			Log.d("Downloading DashboardCategory data..", jsonObject.toString());
			try {
				int sucess = jsonObject.getInt("sucess");
				if(sucess == 1){
					String nextUpdateDate = jsonObject.getString("lastupdatedate");
					
					JSONArray dashboardCategoryArray = jsonObject.getJSONArray("dashboardCategories");
					
					for(int i = 0; i < dashboardCategoryArray.length(); i++){
						JSONObject jsonDashboardCategory = dashboardCategoryArray.getJSONObject(i);
						DashboardCategory dashboardCategory = new DashboardCategory(jsonDashboardCategory.getInt("id"), jsonDashboardCategory.getString("cname"), jsonDashboardCategory.getString("dashboard_id"));
						
						if(dashboardCategoryService.getDashboardCategoryById(dashboardCategory.getId()) != null){
							dashboardCategoryService.updateDashboardCategory(dashboardCategory);
						}else{
							dashboardCategoryService.addDashboardCategory(dashboardCategory);
						}
						
					}
					
					JSONArray dashboardCatDeleteArray = jsonObject.getJSONArray("deleteDashboardCategory");
					for(int i = 0; i < dashboardCatDeleteArray.length(); i++){
						JSONObject jsonDeleteDashboardCat = dashboardCatDeleteArray.getJSONObject(i);
						DashboardCategory dashboardCategory = new DashboardCategory(jsonDeleteDashboardCat.getInt("id"), "", "");
						
						dashboardCategoryService.deleteDashboardCategory(dashboardCategory);
					}
					
					dashboardCategoryService.setUpdateDate(nextUpdateDate);
				}
				
				incremental +=15;
				publishProgress(incremental);
				publishProgress(0);
			} catch (Exception e) {
				Log.e("UBUddyy Error", e.getMessage());
			}
		}

		private void downloadDashboard() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONParser jsonParser = new JSONParser();
			incremental = +15;
			publishProgress(incremental);
			
			String updateDate = dashboardService.getUpdateDate();
			String[] split= updateDate.split("\\s+");
			updateDate = split[0];
			
			JSONObject jsonObject = jsonParser.makeHttpRequest(serverURL+"dashboard/"+updateDate, "GET", params);
			
			incremental += 20;
			publishProgress(incremental);
			Log.d("Downloading Dashboard data..", jsonObject.toString());
			try {
				int sucess = jsonObject.getInt("sucess");
				if(sucess == 1){
					String nextUpdateDate = jsonObject.getString("lastupdatedate");
					
					JSONArray dashboardArray = jsonObject.getJSONArray("dashboards");
					
					for(int i = 0; i < dashboardArray.length(); i++){
						JSONObject jsonDashboard = dashboardArray.getJSONObject(i);
						DashBoard dashboard = new DashBoard(jsonDashboard.getInt("id"), jsonDashboard.getString("dname"), jsonDashboard.getString("tagline"));
						
						if(dashboardService.getDashboardById(dashboard.getId()) != null){
							dashboardService.updateDashboard(dashboard);
						}else{
							dashboardService.addDashboard(dashboard);
						}
						
					}
					
					JSONArray dashboardDeleteArray = jsonObject.getJSONArray("deleteDashboard");
					for(int i = 0; i < dashboardDeleteArray.length(); i++){
						JSONObject jsonDeleteDashboard = dashboardDeleteArray.getJSONObject(i);
						DashBoard dashboard = new DashBoard(jsonDeleteDashboard.getInt("id"), "", "");
						
						dashboardService.deleteDashboard(dashboard);
					}
					
					dashboardService.setUpdateDate(nextUpdateDate);
				}
				
				incremental +=15;
				publishProgress(incremental);
				publishProgress(0);
			} catch (Exception e) {
				Log.e("UBUddyy Error", e.getMessage());
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			Intent intent = new  Intent(UpdateUI.this, DashboardUI.class);
			UpdateUI.this.finish();
			startActivity(intent);
			
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			pb_progressBar.setMax(100);
			loadingTxtView.setVisibility(View.VISIBLE);
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
