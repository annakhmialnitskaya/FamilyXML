package Parsers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) {

		Document doc = null;
		try {
			doc = buildDocument();
		} catch (Exception e) {
			System.out.println("The exception occurred");
		}
		XPathFactory xpathfactory = XPathFactory.newInstance();
		XPath path = xpathfactory.newXPath();
		int totalAgeParents = 0;
		int countParents = 0;
		try {
			totalAgeParents = calculateTotalAgeParents(path, doc);
			countParents = countParents(path, doc);
		} catch (Exception e) {
			System.out.println("The exception occurred");
		}
		double averageAgeParents = calculateAverageAgeParents(totalAgeParents, countParents);
		System.out.println("Средний возраст родителей: " + averageAgeParents);
	}

	private static int calculateTotalAgeParents(XPath path, Document doc) throws XPathExpressionException {
		int sum = 0;
		Node node;
		NodeList nodes = (NodeList) path.evaluate("//age", doc, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			node = nodes.item(i);
			sum = sum + Integer.parseInt(node.getFirstChild().getNodeValue());
		}
		return sum;
	}

	private static Document buildDocument() throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new File("resourses/family2.xml"));
		return doc;
	}

	private static int countParents(XPath path, Document doc) throws XPathExpressionException {
		int count = ((Number) path.evaluate("count(//family/mother|//family/father)", doc, XPathConstants.NUMBER))
				.intValue();
		return count;
	}

	private static double calculateAverageAgeParents(int sum, int count) {
		return (double) sum / count;
	}
}
