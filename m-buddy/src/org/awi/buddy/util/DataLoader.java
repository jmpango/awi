package org.awi.buddy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.awi.buddy.model.DashBoard;
import org.awi.buddy.server.xmladapter.MainBuddyXmlAdapter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public final class DataLoader {
	private static DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
			.newInstance();
	private static DocumentBuilder docBuilder;
	private static Document doc;

	private DataLoader() {
	}

	public static List<DashBoard> getMainBuddyData(InputStream xmlPath)
			throws ParserConfigurationException, SAXException, IOException {
		docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse(xmlPath);
		MainBuddyXmlAdapter mBuddyXmlAdapter = new MainBuddyXmlAdapter(doc);
		return mBuddyXmlAdapter.getItems();
	};
}
