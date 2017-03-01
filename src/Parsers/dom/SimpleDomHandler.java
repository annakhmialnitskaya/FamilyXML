package Parsers.dom;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Parsers.Constants;
import model.Children;
import model.Family;
import model.Father;
import model.Mother;

public class SimpleDomHandler {

	public static void listBuilder(Element root) {
		List<Family> families = new ArrayList<>();
		NodeList familyNodes = root.getElementsByTagName(Constants.FAMILY);
		int familyNodesLength = familyNodes.getLength();
		Family family = null;
		for (int i = 0; i < familyNodesLength; i++) {
			family = new Family();
			Element familyElement = (Element) familyNodes.item(i);
			family.setName(familyElement.getAttribute(Constants.ATTRIBUTE_NAME));

			NodeList motherNodes = familyElement.getElementsByTagName(Constants.MOTHER);
			family.setMother(createMother(motherNodes));

			NodeList fatherNodes = familyElement.getElementsByTagName(Constants.FATHER);
			family.setFather(createFather(fatherNodes));

			families.add(family);
		}
		System.out.println(families);
	}

	private static Mother createMother(NodeList motherNodes) {
		Mother mother = new Mother();
		Element motherElement = (Element) motherNodes.item(0);
		mother.setName(getChildValue(motherElement, Constants.ATTRIBUTE_NAME));
		mother.setMaidenName(getChildValue(motherElement, Constants.ATTRIBUTE_MAIDEN_NAME));
		mother.setSurname(getChildValue(motherElement, Constants.ATTRIBUTE_SURNAME));
		mother.setAge(Integer.parseInt(getChildValue(motherElement, Constants.ATTRIBUTE_AGE)));

		NodeList childrenNodes = motherElement.getElementsByTagName(Constants.CHILDREN);
		Element childrenElement = (Element) childrenNodes.item(0);
		int count = Integer.parseInt(childrenElement.getAttribute(Constants.ATTRIBUTE_COUNT));
		Children children = new Children(count);
		mother.setChildren(children);
		return mother;
	}

	private static Father createFather(NodeList fatherNodes) {
		Father father = new Father();
		Element fatherElement = (Element) fatherNodes.item(0);
		father.setName(getChildValue(fatherElement, Constants.ATTRIBUTE_NAME));
		father.setSurname(getChildValue(fatherElement, Constants.ATTRIBUTE_SURNAME));
		father.setAge(Integer.parseInt(getChildValue(fatherElement, Constants.ATTRIBUTE_AGE)));
		return father;
	}

	private static Element getChild(Element parent, String childName) {
		NodeList nList = parent.getElementsByTagName(childName);
		Element child = (Element) nList.item(0);
		return child;
	}

	private static String getChildValue(Element parent, String childName) {
		Element child = getChild(parent, childName);
		if (child != null) {
			Node node = child.getFirstChild();
			String value = node.getNodeValue();
			return value;
		} else {
			return null;
		}
	}
}
