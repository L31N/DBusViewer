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

import de.soctronic.DBusViewer.DBusInterface;
import de.soctronic.DBusViewer.DBusMethod;
import de.soctronic.DBusViewer.DBusMethodArgument;
import de.soctronic.DBusViewer.Direction;

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

	public List<String> getMethods(String iface) {
		return getElementsOfInterface(iface, "method");
	}
	
	public List<String> getSignals(String iface) {
		return getElementsOfInterface(iface, "signal");
	}
	
	public List<String> getProperties(String iface) {
		return getElementsOfInterface(iface, "property");
	}
	
	public List<DBusMethod> getMethods(String iface, DBusInterface dbusInterface) {
		List<String> methodNames = getElementsOfInterface(iface, "method", "name");
		
		List<DBusMethod> methods = new ArrayList<DBusMethod>();
		for (String methodName : methodNames) {
			List<DBusMethodArgument> methodArguments = getMethodArguments(iface, methodName);
			DBusMethod method = new DBusMethod(methodName, methodArguments, dbusInterface);
			methods.add(method);
		}
		
		return methods;
	}
	
	private List<String> getElementsOfInterface(String iface, String elementName) {
		return getElementsOfInterface(iface, elementName, "name");
	}
	
	private List<String> getElementsOfInterface(String iface, String elementName, String attributeName) {
		List<String> elements = new ArrayList<String>();

		NodeList interfaceNodeList = document.getElementsByTagName("interface");
		for (int i = 0; i < interfaceNodeList.getLength(); i++) {
			Element element = (Element) interfaceNodeList.item(i);
			if (element.getAttribute("name").equals(iface)) {
				NodeList elementNodeList = element.getElementsByTagName(elementName);
				for (int j = 0; j < elementNodeList.getLength(); j++) {
					Element elementMethod = (Element) elementNodeList.item(j);
					elements.add(elementMethod.getAttribute("name"));
				}
			}
		}
		return elements;
	}
	
	private List<DBusMethodArgument> getMethodArguments(String iface, String method) {
		List<DBusMethodArgument> arguments = new ArrayList<DBusMethodArgument>();
		
		NodeList interfaceNodeList = document.getElementsByTagName("interface");
		for (int i = 0; i < interfaceNodeList.getLength(); i++) {
			Element element = (Element) interfaceNodeList.item(i);
			if (element.getAttribute("name").equals(iface)) {
				NodeList elementNodeList = element.getElementsByTagName("method");
				for (int j = 0; j < elementNodeList.getLength(); j++) {
					Element methodElement = (Element) elementNodeList.item(j);
					if (methodElement.getAttribute("name").equals(method)) {
						NodeList argNodeList = methodElement.getElementsByTagName("arg");
						for (int k = 0; k < argNodeList.getLength(); k++) {
							Element argElement = (Element) argNodeList.item(k);
							
							
							String name = argElement.getAttribute("name");
							String type = argElement.getAttribute("type");
							String strDirection = argElement.getAttribute("direction");
							Direction direction = strDirection.equals("in") ? Direction.IN : Direction.OUT;
							
							System.out.println("method[" + method + "] -> arg{" + name + "}{" + type + "}{" + strDirection + "}");
							
							DBusMethodArgument argument = new DBusMethodArgument(name, type, direction);
							arguments.add(argument);					
						}
					}
				}
			}
		}
		
		return arguments;
	}
}
