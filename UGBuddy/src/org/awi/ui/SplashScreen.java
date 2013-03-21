package org.awi.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.ui.server.service.impl.BuddyServiceImpl;
import org.awi.ui.server.util.BuddyContants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;

public class SplashScreen extends BaseActivity {

	private Thread splashThread;
	private boolean isRuning = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_layout);

		if (getIntent().getBooleanExtra("EXIT", false)) {
			if (splashThread != null)
				splashThread = null;
			finish();
		}

		new LoadDataAsyncTask().execute();
	}

	private class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			if (SplashScreen.this.buddyService == null) {
				SplashScreen.this.buddyService = BuddyServiceImpl.getInstance();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			BuddyContants.LOAD_APP_DATA(getAssets(), buddyService);

			splashThread = new Thread() {
				long seconds = 0;

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
						Intent intent = new Intent(SplashScreen.this,
								MainBuddy.class);
						isRuning = false;
						SplashScreen.this.finish();
						SplashScreen.this.moveTaskToBack(true);
						startActivity(intent);
					}
				}
			};

			splashThread.start();

			try {
				this.finalize();
				this.cancel(true);
			} catch (Throwable e) {
			}
		}

	}

	public static class BuddyNamesXmlAdapter {
		private static BuddyNamesXmlAdapter instance;

		private final String KEY_TAG = "buddy";
		private final String KEY_NAME = "name";

		private NodeList nodeList;
		private Document document;
		private List<String> names;

		private BuddyNamesXmlAdapter(AssetManager assetManager) {
			this.names = new ArrayList<String>();
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				document = docBuilder.parse(assetManager
						.open(BuddyContants.BUDDY_NAMES_XML));
			} catch (ParserConfigurationException e) {
			} catch (SAXException e) {
			} catch (IOException e) {
			}

		}

		public List<String> getNames() {
			nodeList = document.getElementsByTagName(KEY_TAG);
			document.getDocumentElement().normalize();
			for (int i = 0; i < nodeList.getLength(); i++) {

				Node _firstDashBoardItemNode = nodeList.item(i);
				if (_firstDashBoardItemNode.getNodeType() == Node.ELEMENT_NODE) {
					Element _firstDashBoardItemElement = (Element) _firstDashBoardItemNode;

					NodeList _nameList = _firstDashBoardItemElement
							.getElementsByTagName(KEY_NAME);
					Element _firstNameElement = (Element) _nameList.item(0);
					NodeList _name = _firstNameElement.getChildNodes();

					names.add(((Node) _name.item(0)).getNodeValue().trim());

				}
			}
			return names;
		}

		public Document get_doc() {
			return document;
		}

		public static synchronized BuddyNamesXmlAdapter getInstance(
				AssetManager as) {
			if (instance == null)
				instance = new BuddyNamesXmlAdapter(as);
			return instance;
		}

	}

}
