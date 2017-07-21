package de.soctronic.DBusViewer;

public class DBusProperty {
	private String name;
	private String type;
	private Permission permission;
	
	private DBusInterface iface;
	
	public DBusProperty(String name, String type, Permission permission, DBusInterface iface) {
		this.name = name;
		this.type = type;
		this.permission = permission;
		this.iface = iface;
	}
	
	public String getName() { return name; }
	public String getType() { return type; }
	public Permission getPermission() { return permission; }
	public DBusInterface getInterface() { return iface; }
}
