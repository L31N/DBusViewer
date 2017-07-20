package de.soctronic.DBusViewer.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	private Document document;

	public XMLParser(String xmlInputString) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			factory.setFeature("http://xml.org/sax/features/namespaces", false);
			factory.setFeature("http://xml.org/sax/features/validation", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			DocumentBuilder builder = factory.newDocumentBuilder();

			ByteArrayInputStream input = new ByteArrayInputStream(xmlInputString.getBytes("UTF-8"));
			document = builder.parse(input);
		} catch (ParserConfigurationException ex) {
			System.err.println("could not configure xml parser: " + ex.getMessage());
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			System.err.println("could not encode xml input: " + ex.getMessage());
			ex.printStackTrace();
		} catch (SAXException | IOException ex) {
			System.err.println("could not parse xml: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public List<String> getNodes() {
		List<String> strNodes = new ArrayList<String>();

		NodeList nodeList = document.getElementsByTagName("node");
		for (int i = 1; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			strNodes.add(element.getAttribute("name"));
		}
		return strNodes;
	}

	public List<String> getInterfaces() {
		List<String> ifaces = new ArrayList<String>();
		
		NodeList nodeList = document.getElementsByTagName("interface");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element) nodeList.item(i);
			ifaces.add(element.getAttribute("name"));
		}
		
		return ifaces;
	}
}
