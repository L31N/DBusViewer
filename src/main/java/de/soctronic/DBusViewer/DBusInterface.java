package de.soctronic.DBusViewer;

import java.util.Map;

public class DBusInterface {

	private String objectPath;

	private DBusNode node;

	private Map<String, DBusMethod> methods;
	private Map<String, DBusProperty> properties;
	private Map<String, DBusSignal> signals;

	public DBusInterface(String name, String objectPath, DBusNode node, Map<String, DBusMethod> methods,
			Map<String, DBusProperty> properties, Map<String, DBusSignal> signals) {
		this.name = name;
		this.objectPath = objectPath;

		this.node = node;

		this.methods = methods;
		this.properties = properties;
		this.signals = signals;
	}

	private String name;

	public String getName() {
		return name;
	}

	public String getObjectPath() {
		return objectPath;
	}

	public DBusNode getNode() {
		return node;
	}

	public Map<String, DBusMethod> getMethods() {
		return methods;
	}

	public Map<String, DBusProperty> getProperties() {
		return properties;
	}

	public Map<String, DBusSignal> getSignals() {
		return signals;
	}
}
