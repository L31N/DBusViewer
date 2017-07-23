package de.soctronic.DBusViewer;

import de.soctronic.DBusViewer.DBus.DBusType;

public class DBusMethodArgument {
	private String name;
	private DBusType type;
	private Direction direction;
	
	public DBusMethodArgument(String name, DBusType type, Direction direction) {
		this.name = name;
		this.type = type;
		this.direction = direction;
	}
	
	public String getName() { return name; }
	public DBusType getType() { return type; }
	public Direction getDirection() { return direction; }
}
