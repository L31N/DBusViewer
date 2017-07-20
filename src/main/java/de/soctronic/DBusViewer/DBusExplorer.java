package de.soctronic.DBusViewer;

import java.util.ArrayList;
import java.util.List;

import javax.management.modelmbean.XMLParseException;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.soctronic.DBusViewer.DBus.DBus;
import de.soctronic.DBusViewer.util.XMLParser;

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
		System.out.println("discovering tree for bus[" + busname + "]");
		DBusTree dbusTree = new DBusTree(busname);

		try {
			Introspectable introspectable = (Introspectable) dbusConnection.getRemoteObject(busname, "/", Introspectable.class);
			String introspectionResult = introspectable.Introspect();
			XMLParser xmlParser = new XMLParser();
			List<String> nodes = xmlParser.getNodes(introspectionResult);
			
			dbusTree.addNode(discoverNode("/"));
			
			for (String objectPath : nodes) {
				dbusTree.addNode(discoverNode(objectPath));
			}
			
		} catch (DBusException ex) {
			System.err.println("could not introspect[" + busname + "]: " + ex.getMessage());
			ex.printStackTrace();
		}

		return dbusTree;
	}
	
	private DBusNode discoverNode(String objectPath) {
		return null;
		
	}
}
