package de.soctronic.DBusViewer;

import java.util.List;

public class DBusMethod {
	private String name;
	List<DBusMethodArgument> arguments;
	
	private DBusInterface iface;
	
	public DBusMethod(String name, List<DBusMethodArgument> arguments, DBusInterface iface) {
		this.name = name;
		this.arguments = arguments;
		
		this.iface = iface;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public List<DBusMethodArgument> getArguments() {
		return arguments;
	}

	public DBusInterface getIface() {
		return iface;
	}
}
