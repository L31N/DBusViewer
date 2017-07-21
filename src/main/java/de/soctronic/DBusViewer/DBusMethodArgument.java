package de.soctronic.DBusViewer;

public class DBusMethodArgument {
	private String name;
	private String type;
	private Direction direction;
	
	public DBusMethodArgument(String name, String type, Direction direction) {
		this.name = name;
		this.type = type;
		this.direction = direction;
	}
	
	public String getName() { return name; }
	public String getType() { return type; }
	public Direction getDirection() { return direction; }
}
