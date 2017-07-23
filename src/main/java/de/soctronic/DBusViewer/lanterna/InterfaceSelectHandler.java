package de.soctronic.DBusViewer.lanterna;

import java.util.List;

import com.googlecode.lanterna.gui2.table.Table;

import de.soctronic.DBusViewer.DBusNode;
import de.soctronic.DBusViewer.DBusTree;

public class InterfaceSelectHandler implements Runnable {

	private MainWindow window;
	private DBusTree dbusTree;
	private DBusNode node;
	private Table<String> table;
	
	public InterfaceSelectHandler(MainWindow window, DBusTree dbusTree, DBusNode node, Table<String> table) {
		this.window = window;
		this.dbusTree = dbusTree;
		this.node = node;
		this.table = table;
	}
	
	@Override
	public void run() {
		List<String> data = table.getTableModel().getRow(table.getSelectedRow());
		window.onInterfaceSelect(dbusTree, node, data.get(0));	
	}

}
