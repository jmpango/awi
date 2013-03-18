package org.awi.ui.server.adpter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.awi.ui.model.DashBoard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DashboardXmlAdapter {

	private final String KEY_TAG = "item";
	private final String KEY_ID = "id";
	private final String KEY_NAME = "name";
	private final String KEY_TAGLINE = "tagline";

	private NodeList dashBoardNodeList;
	private Document document;
	private List<DashBoard> dashBoardList;

	public DashboardXmlAdapter(Document document)
			throws ParserConfigurationException, SAXException, IOException {
		this.document = document;
		
		this.dashBoardList = new ArrayList<DashBoard>();
		dashBoardNodeList = document.getElementsByTagName(KEY_TAG);
		document.getDocumentElement().normalize();
	}

	public List<DashBoard> getItems() {
		DashBoard dashBoard = null;
		for (int i = 0; i < dashBoardNodeList.getLength(); i++) {

			dashBoard = new DashBoard();
			Node _firstDashBoardItemNode = dashBoardNodeList.item(i);
			if (_firstDashBoardItemNode.getNodeType() == Node.ELEMENT_NODE) {
				Element _firstDashBoardItemElement = (Element) _firstDashBoardItemNode;

				NodeList _idList = _firstDashBoardItemElement
						.getElementsByTagName(KEY_ID);
				Element _firstIdElement = (Element) _idList.item(0);
				NodeList _id = _firstIdElement.getChildNodes();

				// -- id
				dashBoard.setId(((Node) _id.item(0)).getNodeValue().trim());

				NodeList _nameList = _firstDashBoardItemElement
						.getElementsByTagName(KEY_NAME);
				Element _firstNameElement = (Element) _nameList.item(0);
				NodeList _name = _firstNameElement.getChildNodes();

				// -- name
				dashBoard.setName(((Node) _name.item(0)).getNodeValue().trim());

				NodeList _taglineList = _firstDashBoardItemElement
						.getElementsByTagName(KEY_TAGLINE);
				Element _firstTaglineElement = (Element) _taglineList.item(0);
				NodeList _tagLine = _firstTaglineElement.getChildNodes();

				// -- tagline
				dashBoard.setTagLine(((Node) _tagLine.item(0)).getNodeValue()
						.trim());

				dashBoardList.add(dashBoard);

			}
		}
		return dashBoardList;
	}

	public Document get_doc() {
		return document;
	}

}
