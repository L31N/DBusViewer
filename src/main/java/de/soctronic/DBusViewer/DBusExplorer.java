package de.soctronic.DBusViewer;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.DBus.Introspectable;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.DBusExecutionException;

import Exception.DBusExplorationException;
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

	public DBusTree discoverTree(String busname) throws DBusExplorationException {
		System.out.println("discovering tree for bus[" + busname + "]");
		DBusTree dbusTree = new DBusTree(busname);

		// recursive procedure
		dbusTree.addNode(discoverNode(dbusTree, "/"));

		return dbusTree;

	}

	private DBusNode discoverNode(DBusTree dbusTree, String objectPath) throws DBusExplorationException {
		List<String> ifaces = new ArrayList<String>();
		List<String> childs = new ArrayList<String>();

		String introspectionResult = introspect(dbusTree.getBusName(), objectPath);
		System.out.println(introspectionResult);
		XMLParser xmlParser = new XMLParser(introspectionResult);
		ifaces = xmlParser.getInterfaces();
		childs = xmlParser.getNodes();

		DBusNode node = new DBusNode(objectPath, dbusTree);
		for (String iface : ifaces) {
			node.addInterface(discoverInterface(xmlParser, dbusTree.getBusName(), iface, node));
		}

		for (String child : childs) {
			String path = objectPath + "/" + child;
			path = path.replaceAll("//", "/");
			node.addNode(discoverNode(dbusTree, path));
		}

		return node;
	}

	private DBusInterface discoverInterface(XMLParser xmlParser, String busname, String iface, DBusNode node) {

		DBusInterface dbusInterface = new DBusInterface(iface, node);

		// String introspectionResult = introspect(busname,
		// node.getObjectPath());
		// XMLParser xmlParser = new XMLParser(introspectionResult);
		List<DBusMethod> methods = xmlParser.getMethods(iface, dbusInterface);
		List<DBusSignal> signals = xmlParser.getSignals(iface, dbusInterface);
		List<DBusProperty> properties = xmlParser.getProperties(iface, dbusInterface);

		for (DBusMethod method : methods) {
			dbusInterface.addMethod(method);
		}
		for (DBusSignal signal : signals) {
			dbusInterface.addSignal(signal);
		}
		for (DBusProperty property : properties) {
			dbusInterface.addProperty(property);
		}

		return dbusInterface;
	}

	private String introspect(String busname, String objectPath) throws DBusExplorationException {
		try {
			Introspectable introspectable = (Introspectable) dbusConnection.getRemoteObject(busname, objectPath,
					Introspectable.class);
			return introspectable.Introspect();
		} catch (DBusException | DBusExecutionException ex) {
			System.err.println("could not call introspection on [" + objectPath + "]: " + ex.getMessage());
			ex.printStackTrace();
			throw new DBusExplorationException("could not call introspection on [" + objectPath + "]");
		}
	}
}
