package de.soctronic.DBusViewer.DBus;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;

@DBusInterfaceName("org.freedesktop.DBus")
public interface DBus extends org.freedesktop.DBus {
	
	public static final String BUSNAME = "org.freedesktop.DBus";
	public static final String OBJECT_PATH = "/org/freedesktop/DBus";
	
	String[] ListActivatableNames();
}