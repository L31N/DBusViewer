package de.soctronic.DBusViewer;

import java.util.Map;

public class DBusNode {
	private String objectPath;
	private Map<String, DBusInterface> interfaces;
	
	public DBusNode(String objectPath, Map<String, DBusInterface> interfaces) {
		this.objectPath = objectPath;
		this.interfaces = interfaces;
	}

	public String getObjectPath() {
		return objectPath;
	}

	public Map<String, DBusInterface> getInterfaces() {
		return interfaces;
	}
}
