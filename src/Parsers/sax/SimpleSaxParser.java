package Parsers.sax;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import Parsers.Constants;

public class SimpleSaxParser {

	public static void main(String[] args) {

		try {
			SimpleSaxHandler handler = new SimpleSaxHandler();

			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);

			try {
				reader.parse(Constants.FILE_PATH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

}
