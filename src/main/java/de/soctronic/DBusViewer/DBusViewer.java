package de.soctronic.DBusViewer;

import org.freedesktop.DBus;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import de.soctronic.DBusViwer.bluez.ObjectManager;
import de.soctronic.DBusViwer.bluez.Properties;

public class DBusViewer {

	private final static String BUSNAME = "org.bluez";
	private final static String ADAPTER_OBJECTPATH = "/org/bluez/hci0";
	private final static String ADAPTER_INTERFACE = "org.bluez.Adapter1";
	
	public static void main(String[] args) {
		try {
			DBusConnection dbusConnection = DBusConnection.getConnection(DBusConnection.SYSTEM);
			Properties adapterProperties = dbusConnection.getRemoteObject(BUSNAME, ADAPTER_OBJECTPATH, Properties.class);
			System.out.println(adapterProperties.GetAll(ADAPTER_INTERFACE));
			
		} catch (DBusException ex) {
			System.err.println("could not open dbus connection: ");
			ex.printStackTrace();
		}
	}
}