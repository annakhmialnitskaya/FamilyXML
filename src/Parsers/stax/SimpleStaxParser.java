package Parsers.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import Parsers.Constants;

public class SimpleStaxParser {
	public static void main(String[] args) {

		SimpleStaxHandler handler = new SimpleStaxHandler();
		InputStream input = null;
		try {
			input = new FileInputStream(Constants.FILE_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			handler.parse(input);
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
}
