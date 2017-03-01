package Parsers.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Parsers.Constants;
import model.Children;
import model.Family;
import model.Father;
import model.Mother;

public class SimpleDomParser2 {

	public static void main(String[] args) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		List<Family> families = null;

		try {
			builder = dbf.newDocumentBuilder();
			Document document = builder.parse("resourses/family2.xml");

			Element root = document.getDocumentElement();
			families = new ArrayList<>();
			Family family = null;
			NodeList familyElements = root.getElementsByTagName("family");

			for (int i = 0; i < familyElements.getLength(); i++) {
				family = new Family();
				families.add(family);

				Element familyElement = (Element) familyElements.item(i);
				family.setName(familyElement.getAttribute("name"));

				NodeList parentElements = familyElement.getChildNodes();

				for (int j = 0; j < parentElements.getLength(); j++) {
					Node elementParent = parentElements.item(j);
					System.out.println(elementParent.getNodeName());

					if (elementParent.getNodeName().equals(Constants.MOTHER)) {
						NodeList elements = elementParent.getChildNodes();
						Mother mother = new Mother();
						family.setMother(mother);

						for (int k = 0; k < elements.getLength(); k++) {
							String elementValue = null;
							Node element = elements.item(k);
							Node nodeChild = element.getFirstChild();
							if (nodeChild != null) {
								elementValue = nodeChild.getNodeValue();
								System.out.println(element.getNodeName() + " " + elementValue);

								switch (element.getNodeName()) {
								case Constants.ATTRIBUTE_AGE:
									mother.setAge(Integer.parseInt(elementValue));
									break;
								case Constants.ATTRIBUTE_SURNAME:
									mother.setSurname(elementValue);
									break;
								case Constants.ATTRIBUTE_MAIDEN_NAME:
									mother.setMaidenName(elementValue);
									break;
								case Constants.ATTRIBUTE_NAME:
									mother.setName(elementValue);
									break;
								default:
									break;
								}
							} else {
								if (element.getAttributes() != null) {
									int count = Integer
											.parseInt(((Element) element).getAttribute(Constants.ATTRIBUTE_COUNT));
									Children children = new Children(count);
									mother.setChildren(children);
								}
							}
						}

					} else if (elementParent.getNodeName().equals(Constants.FATHER)) {
						NodeList elements = elementParent.getChildNodes();
						Father father = new Father();
						family.setFather(father);

						for (int k = 0; k < elements.getLength(); k++) {
							String elementValue = null;
							Node element = elements.item(k);
							Node nodeChild = element.getFirstChild();
							if (nodeChild != null) {
								elementValue = nodeChild.getNodeValue();
								System.out.println(element.getNodeName() + " " + elementValue);

								switch (element.getNodeName()) {
								case Constants.ATTRIBUTE_AGE:
									father.setAge(Integer.parseInt(elementValue));
									break;
								case Constants.ATTRIBUTE_SURNAME:
									father.setSurname(elementValue);
									break;
								case Constants.ATTRIBUTE_NAME:
									father.setName(elementValue);
									break;
								default:
									break;
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(families);
	}
}
