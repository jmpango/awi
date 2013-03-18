package org.awi.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.ui.model.Buddy;
import org.awi.ui.model.Globals;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_layout);
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
			BuddyNamesXmlAdapter instance = BuddyNamesXmlAdapter
					.getInstance(getAssets());
			List<String> names = instance.getNames();
			Globals globals = Globals.getInstance();

			List<Buddy> buddies = new ArrayList<Buddy>();

			for (String name : names) {
				buddies = buddyService.getBuddies(getAssets(), "buddies/"
						+ name + BuddyContants.DEFAULT_XML_EXTENTION, name);
				globals.addGlobalData(name, buddies);
			}

			Thread splashThread = new Thread() {
				long seconds = 0;

				public void run() {
					try {
						while (true && seconds < 5000) {
							if (!false)
								seconds += 100;
							sleep(100);
						}
					} catch (Exception e) {
					} finally {
						Intent intent = new Intent(SplashScreen.this,
								MainBuddy.class);
						SplashScreen.this.finish();
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

		private final String KEY_TAG = "buddyName";
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
