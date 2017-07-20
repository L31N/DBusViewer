package de.soctronic.DBusViewer;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.soctronic.DBusViewer.DBus.DBus;

public class DBusExplorer {
	
	private DBusConnection dbusConnection;
	
	public DBusExplorer(DBusConnection dbusConnection) {
		this.dbusConnection = dbusConnection;
	}

	public List<String> discoverBusNames() {
		List<String> nameList = new ArrayList<String>();

		try {
			DBus dbus = dbusConnection.getRemoteObject(DBus.BUSNAME, DBus.OBJECT_PATH, DBus.class);
			String[] dbusNames = dbus.ListActivatableNames();
			for (String name : dbusNames) {
				nameList.add(name);
			}
		} catch (DBusException ex) {
			System.err.println("could not get DBus remote object: " + ex.getMessage());
			ex.printStackTrace();
		}

		return nameList;
	}
	
	public DBusTree discoverTree(String busname) {
		return null;
	}
}
