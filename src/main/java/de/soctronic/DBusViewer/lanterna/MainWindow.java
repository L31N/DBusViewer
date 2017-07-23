package de.soctronic.DBusViewer.lanterna;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.types.DBusStructType;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;

import de.soctronic.DBusViewer.DBusExplorer;
import de.soctronic.DBusViewer.DBusInterface;
import de.soctronic.DBusViewer.DBusNode;
import de.soctronic.DBusViewer.DBusTree;

public class MainWindow extends BasicWindow {

	private DBusExplorer dbusExplorer;

	private Panel mainPanel;
	private Panel controlPanel;
	private Panel treePanel;

	public MainWindow() {
		super("DBusViewer 1.0.0-SNAPSHOT");

		try {
			DBusConnection dbusConnection = DBusConnection.getConnection(DBusConnection.SYSTEM);
			dbusExplorer = new DBusExplorer(dbusConnection);
		} catch (DBusException ex) {
			System.err.println("could not get DBusConnection: " + ex.getMessage());
			ex.printStackTrace();
		}

		displayBusNames();
	}

	private void displayBusNames() {
		mainPanel = new Panel();
		controlPanel = new Panel();
		treePanel = new Panel();

		mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

		Button exitButton = new Button("Exit", new Runnable() {
			public void run() {
				MainWindow.this.close();
			}
		});

		// table = new Table<String>("available bus names");
		final Table<String> table = new Table<String>("Available Bus Names");

		List<String> busnames = dbusExplorer.discoverBusNames();
		for (String name : busnames) {
			table.getTableModel().addRow(name);
		}

		table.setSelectAction(new BusnameSelectHandler(this, table));

		controlPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
		controlPanel.addComponent(table.withBorder(Borders.doubleLine()));
		controlPanel.addComponent(exitButton);

		mainPanel.addComponent(controlPanel);
		mainPanel.addComponent(treePanel);

		this.setComponent(mainPanel);
	}

	public void onBusnameSelect(String busname) {
		DBusTree dbusTree = dbusExplorer.discoverTree(busname);
		displayTree(dbusTree);
	}

	private void displayTree(DBusTree dbusTree) {
		treePanel.removeAllComponents();
		Table<String> nodeTable = new Table<String>("DBus Node View ## [ " + dbusTree.getBusName() + " ]");
		List<String> nodeNames = new ArrayList<String>(dbusTree.getNodes().keySet());
		for (String nodeName : nodeNames) {
			nodeTable.getTableModel().addRow(nodeName);
		}

		nodeTable.setSelectAction(new NodeSelectHandler(this, dbusTree, nodeTable));

		//treePanel.addComponent(nodeTable.withBorder(Borders.singleLine()));
		treePanel.addComponent(nodeTable);
		nodeTable.takeFocus();
	}

	public void onNodeSelect(DBusTree dbusTree, String node) {
		treePanel.removeAllComponents();
		Table<String> interfaceTable = new Table<String>(
				"DBus Interface View ## [ " + dbusTree.getBusName() + " --> " + node + " ]");
		Set<String> dbusInterfaces = dbusTree.getNodes().get(node).getInterfaces().keySet();
		for (String interfacename : dbusInterfaces) {
			interfaceTable.getTableModel().addRow(interfacename);
		}

		interfaceTable.setSelectAction(
				new InterfaceSelectHandler(this, dbusTree, dbusTree.getNodes().get(node), interfaceTable));

		//treePanel.addComponent(interfaceTable.withBorder(Borders.singleLine()));
		treePanel.addComponent(interfaceTable);
		interfaceTable.takeFocus();
	}

	public void onInterfaceSelect(DBusTree dbusTree, DBusNode node, String interfaceName) {
		treePanel.removeAllComponents();

		Table<String> table = new Table<String>("DBus Detail View ## [ " + dbusTree.getBusName() + " --> "
				+ node.getObjectPath() + " --> " + interfaceName);

		// add methods

		// add signals

		// add properties

		treePanel.addComponent(table);
		table.takeFocus();
	}
}
