package de.soctronic.DBusViewer;

public class DBusSignal {
	private String name;

	private DBusInterface iface;

	public DBusSignal(String name, DBusInterface iface) {
		this.name = name;

		this.iface = iface;
	}

	public String getName() {
		return name;
	}

	public DBusInterface getInterface() {
		return iface;
	}
}
