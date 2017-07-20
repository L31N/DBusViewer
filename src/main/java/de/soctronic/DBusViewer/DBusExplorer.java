package de.soctronic.DBusViewer;

import java.util.ArrayList;
import java.util.List;

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

		// recursive procedure
		dbusTree.addNode(discoverNode(busname, "/"));

		return dbusTree;

	}

	private DBusNode discoverNode(String busname, String objectPath) {
		List<String> ifaces = new ArrayList<String>();
		List<String> childs = new ArrayList<String>();
		try {
			Introspectable introspectable = (Introspectable) dbusConnection.getRemoteObject(busname, objectPath,
					Introspectable.class);
			String introspectionResult = introspectable.Introspect();
			XMLParser xmlParser = new XMLParser(introspectionResult);
			ifaces = xmlParser.getInterfaces();
			childs = xmlParser.getNodes();
		} catch (DBusException ex) {
			System.err.println("could not call introspection on [" + objectPath + "]: " + ex.getMessage());
			ex.printStackTrace();
		}

		DBusNode node = new DBusNode(objectPath);
		for (String iface : ifaces) {
			System.out.println("new Interface discovered[" + iface + "]");
			node.addInterface(discoverInterface(busname, iface, node));
		}

		for (String child : childs) {
			String path = objectPath + "/" + child;
			path = path.replaceAll("//", "/");
			node.addNode(discoverNode(busname, path));
		}

		return node;
	}
	
	private DBusInterface discoverInterface(String busname, String name, DBusNode node) {
		
		
		return null;
	}
}
