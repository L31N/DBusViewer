package de.soctronic.DBusViewer;

import de.soctronic.DBusViewer.DBus.DBusType;

public class DBusSignalArgument {
	private String name;
	private DBusType type;
	
	public DBusSignalArgument(String name, DBusType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() { return name; }
	public DBusType getType() { return type; }
}
