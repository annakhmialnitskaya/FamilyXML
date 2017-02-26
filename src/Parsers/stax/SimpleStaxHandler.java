package Parsers.stax;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import model.Children;
import model.Family;
import model.Father;
import model.Mother;
import model.Parent;
import model.XmlElement;

public class SimpleStaxHandler {

	private Family family;
	private Parent parent;
	private List<Family> families;
	private XmlElement currentNode;

	public void parse(InputStream input) throws XMLStreamException {
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
		process(reader);
		System.out.println(families);
	}

	public void process(XMLStreamReader reader) throws XMLStreamException {
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				startElement(reader);
				break;
			case XMLStreamConstants.CHARACTERS:
				characters(reader);
				break;
			}
		}
	}

	private void startElement(XMLStreamReader reader) {
		String name = reader.getLocalName();
		currentNode = XmlElement.valueOf(name.toUpperCase());
		switch (currentNode) {
		case FAMILIES:
			families = new ArrayList<Family>();
			break;
		case FAMILY:
			family = new Family(reader.getAttributeValue(null, XmlElement.NAME.name().toLowerCase()));
			families.add(family);
			break;
		case MOTHER:
			parent = new Mother();
			family.setMother((Mother) parent);
			break;
		case FATHER:
			parent = new Father();
			family.setFather((Father) parent);
			break;
		case CHILDREN:
			Children children = new Children(
					Integer.parseInt(reader.getAttributeValue(null, XmlElement.COUNT.name().toLowerCase())));
			((Mother) parent).setChildren(children);
			break;
		default:
			break;
		}
	}

	private void characters(XMLStreamReader reader) {
		String text = reader.getText().trim();
		if (text != null && !text.equals("")) {
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
	}
}
