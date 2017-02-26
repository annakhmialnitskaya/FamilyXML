package Parsers.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Parsers.Constants;
import model.Children;
import model.Family;
import model.Father;
import model.Mother;
import model.Parent;
import model.XmlElement;

public class SimpleSaxHandler extends DefaultHandler {

	private String text;
	private Family family;
	private Parent parent;
	private List<Family> families;
	private XmlElement currentNode;

	@Override
	public void characters(char[] buf, int start, int length) throws SAXException {
		text = new StringBuilder().append(buf, start, length).toString().trim();
		if (text == null || text.equals("")) {
			return;
		}
		switch (currentNode) {
		case AGE:
			parent.setAge(Integer.parseInt(text));
			break;
		case SURNAME:
			parent.setSurname(text);
			break;
		case MAIDEN_NAME:
			((Mother) parent).setMaidenName(text);
			break;
		case NAME:
			parent.setName(text);
			break;
		default:
			break;
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case Constants.FAMILY:
			family = new Family(attributes.getValue(0));
			families.add(family);
			break;
		case Constants.MOTHER:
			parent = new Mother();
			family.setMother((Mother) parent);
			break;
		case Constants.FATHER:
			parent = new Father();
			family.setFather((Father) parent);
			break;
		case Constants.CHILDREN:
			Children children = new Children(Integer.parseInt(attributes.getValue(0)));
			((Mother) parent).setChildren(children);
			break;
		default:
			break;
		}
		currentNode = XmlElement.valueOf(qName.toUpperCase());
	}

	@Override
	public void startDocument() throws SAXException {
		families = new ArrayList<Family>();
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println(families);
	}
}
