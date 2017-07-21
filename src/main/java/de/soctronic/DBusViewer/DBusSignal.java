package de.soctronic.DBusViewer;

import java.util.List;

public class DBusSignal {
	private String name;
	private List<DBusSignalArgument> arguments;

	private DBusInterface iface;

	public DBusSignal(String name, List<DBusSignalArgument> arguments, DBusInterface iface) {
		this.name = name;
		this.arguments = arguments;

		this.iface = iface;
	}

	public String getName() {
		return name;
	}
	
	public List<DBusSignalArgument> getArguments() { return arguments; }

	public DBusInterface getInterface() {
		return iface;
	}
}
