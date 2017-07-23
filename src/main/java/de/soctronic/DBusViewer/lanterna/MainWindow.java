package de.soctronic.DBusViewer.lanterna;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.types.DBusMapType;
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

import Exception.DBusExplorationException;
import de.soctronic.DBusViewer.DBusExplorer;
import de.soctronic.DBusViewer.DBusInterface;
import de.soctronic.DBusViewer.DBusMethod;
import de.soctronic.DBusViewer.DBusMethodArgument;
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
		try {
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
		} catch (RuntimeException ex) {
			System.err.println("an error occured: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void onBusnameSelect(String busname) {
		try {
			DBusTree dbusTree = dbusExplorer.discoverTree(busname);
			displayTree(dbusTree);
		} catch (DBusExplorationException ex) {
			System.err.println("could not discover DBusTree: " + ex.getMessage());
		}
	}

	private void displayTree(DBusTree dbusTree) {
		try {
			treePanel.removeAllComponents();
			Table<String> nodeTable = new Table<String>("DBus Node View ## [ " + dbusTree.getBusName() + " ]");
			List<String> nodeNames = new ArrayList<String>(dbusTree.getNodes().keySet());
			for (String nodeName : nodeNames) {
				nodeTable.getTableModel().addRow(nodeName);
			}

			nodeTable.setSelectAction(new NodeSelectHandler(this, dbusTree, nodeTable));

			// treePanel.addComponent(nodeTable.withBorder(Borders.singleLine()));
			treePanel.addComponent(nodeTable);
			nodeTable.takeFocus();
		} catch (RuntimeException ex) {
			System.err.println("could not discover dbusTree: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void onNodeSelect(DBusTree dbusTree, String node) {
		treePanel.removeAllComponents();
		Table<String> interfaceTable = new Table<String>(
				"DBus Interface View ## [ " + dbusTree.getBusName() + " --> " + node + " ]");
		Set<String> dbusInterfaces = dbusTree.getNodes().get(node).getInterfaces().keySet();
		for (String interfacename : dbusInterfaces) {
			interfaceTable.getTableModel().addRow(interfacename);
		}

		interfaceTable.setSelectAction(new InterfaceSelectHandler(this, dbusTree.getNodes().get(node), interfaceTable));

		// treePanel.addComponent(interfaceTable.withBorder(Borders.singleLine()));
		treePanel.addComponent(interfaceTable);
		interfaceTable.takeFocus();
	}

	public void onInterfaceSelect(DBusNode node, String interfaceName) {
		treePanel.removeAllComponents();

		// add methods
		// TODO: add lable
		Table<String> methodTable = new Table<String>("Methods");
		List<DBusMethod> methods = new ArrayList(node.getInterfaces().get(interfaceName).getMethods().values());
		for (DBusMethod method : methods) {
			String str = method.getName() + " (";
			boolean removeDelimiter = false;
			int argIndex = 0;
			DBusMethodArgument retval = null;
			for (DBusMethodArgument arg : method.getArguments()) {
				if (arg.getDirection() == de.soctronic.DBusViewer.Direction.IN) {
					if (arg.getName() != "")
						str += arg.getType().toString() + " " + arg.getName() + ", ";
					else {
						str += arg.getType().toString() + " arg" + argIndex + ", ";
						argIndex++;
					}
					removeDelimiter = true;
				} else
					retval = arg;
			}
			if (removeDelimiter)
				str = str.substring(0, str.length() - 3);
			str += ")";
			if (retval != null) {
				str += " --> (" + retval.getType() + " " + retval.getName() + ")";
				if (retval.getName() == "") {
					str = str.substring(0, str.length() - 3);
					str += ")";
				}
			} else {
				str += " --> ()";
			}

			methodTable.getTableModel().addRow(str);
		}

		// add signals

		// add properties

		treePanel.addComponent(methodTable);
		methodTable.takeFocus();
	}
}
