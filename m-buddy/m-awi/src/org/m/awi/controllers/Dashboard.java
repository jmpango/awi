package org.m.awi.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.m.awi.R;
import org.m.awi.data.binder.DashboardDataBinder;
import org.m.awi.xml.adapters.DashboardXmlAdapter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Dashboard extends Activity {
	
	private DashboardXmlAdapter _dashboardXMLAdapter;
	private List<HashMap<String, String>> _dashBoardData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_view);
		
		
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	        Document doc = docBuilder.parse (getAssets().open("dashboard.xml"));
	        
	        _dashboardXMLAdapter = new DashboardXmlAdapter(doc);
	        _dashBoardData = _dashboardXMLAdapter.getItems();
	        
	        DashboardDataBinder bindingData = new DashboardDataBinder(this, _dashBoardData);
	        
	        ListView listView = (ListView) findViewById(R.id.dashboard_list);
	        listView.setAdapter(bindingData);
	        
	        //set listner
	      /*  list.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent i = new Intent();
					i.setClass(WeatherActivity.this, SampleActivity.class);

					// parameters
					i.putExtra("position", String.valueOf(position + 1));
					
					 selected item parameters
					 * 1.	City name
					 * 2.	Weather
					 * 3.	Wind speed
					 * 4.	Temperature
					 * 5.	Weather icon   
					 
					i.putExtra("city", weatherDataCollection.get(position).get(KEY_CITY));
					i.putExtra("weather", weatherDataCollection.get(position).get(KEY_CONDN));
					i.putExtra("windspeed", weatherDataCollection.get(position).get(KEY_SPEED));
					i.putExtra("temperature", weatherDataCollection.get(position).get(KEY_TEMP_C));
					i.putExtra("icon", weatherDataCollection.get(position).get(KEY_ICON));

					// start the sample activity
					startActivity(i);
				}
			});*/

	        
	        
	        
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dashboard_menu, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//Toast.makeText(this, "MenuItem " + item.getTitle() + " selected.", Toast.LENGTH_SHORT).show();
		return false;
	}

	public List<HashMap<String, String>> get_dashBoardData() {
		return _dashBoardData;
	}

	public void set_dashBoardData(List<HashMap<String, String>> _dashBoardData) {
		this._dashBoardData = _dashBoardData;
	}
}
