package de.soctronic.DBusViewer;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.soctronic.DBusViewer.DBus.DBus;
import de.soctronic.DBusViewer.lanterna.DBusViewerGUI;


public class DBusViewer {

	private final static String BUSNAME = "org.bluez";
	private final static String ADAPTER_OBJECTPATH = "/org/bluez/hci0";
	private final static String ADAPTER_INTERFACE = "org.bluez.Adapter1";
	
	private DBusConnection dbusConnection;
	
	public static void main(String[] args) {
		DBusViewer dbusViewer = new DBusViewer();
		List<String> dbusNames = dbusViewer.discoverDBusNames();
				
		DBusViewerGUI dBusViewerGUI = new DBusViewerGUI();
		dBusViewerGUI.displayBusNames(dbusNames);
	}
	
	public DBusViewer() {
		try {
			dbusConnection = DBusConnection.getConnection(DBusConnection.SYSTEM);
			//Properties adapterProperties = dbusConnection.getRemoteObject(BUSNAME, ADAPTER_OBJECTPATH, Properties.class);
			//System.out.println(adapterProperties.GetAll(ADAPTER_INTERFACE));
			
		} catch (DBusException ex) {
			System.err.println("could not open dbus connection: ");
			ex.printStackTrace();
		}
	}
	
	public List<String> discoverDBusNames() {
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
}