package de.soctronic.DBusViewer;

import java.util.HashMap;
import java.util.Map;

public class DBusInterface {

	private DBusNode node;

	private Map<String, DBusMethod> methods;
	private Map<String, DBusProperty> properties;
	private Map<String, DBusSignal> signals;

	public DBusInterface(String name, DBusNode node) {
		this.name = name;
		this.node = node;
		
		this.methods = new HashMap<String, DBusMethod>();
		this.properties = new HashMap<String, DBusProperty>();
		this.signals = new HashMap<String, DBusSignal>();
	}
	
	public DBusInterface(String name, DBusNode node, Map<String, DBusMethod> methods,
			Map<String, DBusProperty> properties, Map<String, DBusSignal> signals) {
		this.name = name;

		this.node = node;

		this.methods = methods;
		this.properties = properties;
		this.signals = signals;
	}

	private String name;

	public String getName() {
		return name;
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
	
	public void addMethod(DBusMethod method) {
		this.methods.put(method.getName(), method);
	}
	
	public void addProperty(DBusProperty property) {
		this.properties.put(property.getName(), property);
	}
	
	public void addSignal(DBusSignal signal) {
		this.signals.put(signal.getName(), signal);
	}
}
