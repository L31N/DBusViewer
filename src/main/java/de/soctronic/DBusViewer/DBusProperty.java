package de.soctronic.DBusViewer;

import org.freedesktop.dbus.Variant;

public class DBusProperty {
	private String name;
	private Variant value;
	private Permission permission;
	
	private DBusInterface iface;
	
	public DBusProperty(String name, Variant value, Permission permission, DBusInterface iface) {
		this.name = name;
		this.value = value;
		this.permission = permission;
		this.iface = iface;
	}
	
	public String getName() { return name; }
	public Variant getValue() { return value; }
	public Permission getPermission() { return permission; }
	public DBusInterface getInterface() { return iface; }
}
