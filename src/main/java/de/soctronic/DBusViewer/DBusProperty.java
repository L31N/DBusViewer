package de.soctronic.DBusViewer;

import de.soctronic.DBusViewer.DBus.DBusType;

public class DBusProperty {
	private String name;
	private DBusType type;
	private Permission permission;
	
	private DBusInterface iface;
	
	public DBusProperty(String name, DBusType type, Permission permission, DBusInterface iface) {
		this.name = name;
		this.type = type;
		this.permission = permission;
		this.iface = iface;
	}
	
	public String getName() { return name; }
	public DBusType getType() { return type; }
	public Permission getPermission() { return permission; }
	public DBusInterface getInterface() { return iface; }
}
