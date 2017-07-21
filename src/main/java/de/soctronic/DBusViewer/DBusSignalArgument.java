package de.soctronic.DBusViewer;

public class DBusSignalArgument {
	private String name;
	private String type;
	
	public DBusSignalArgument(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() { return name; }
	public String getType() { return type; }
}
