package de.soctronic.DBusViewer;

import java.util.List;

import org.freedesktop.dbus.Variant;

public class DBusMethod {
	private String name;
	List<Variant> arguments;
	Variant retval;
	
	private DBusInterface iface;
	
	public DBusMethod(String name, List<Variant> arguments, Variant retval, DBusInterface iface) {
		this.name = name;
		this.arguments = arguments;
		this.retval = retval;
		
		this.iface = iface;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	public List<Variant> getArguments() {
		return arguments;
	}

	public Variant getRetval() {
		return retval;
	}

	public DBusInterface getIface() {
		return iface;
	}
}
