package de.soctronic.DBusViewer.lanterna;

import java.util.List;

import com.googlecode.lanterna.gui2.table.Table;

import de.soctronic.DBusViewer.DBusTree;

public class NodeSelectHandler implements Runnable {

	private MainWindow window;
	private DBusTree dbusTree;
	private Table<String> nodeTable;
	
	public NodeSelectHandler(MainWindow window, DBusTree dbusTree, Table<String> nodeTable) {
		this.window = window;
		this.dbusTree = dbusTree;
		this.nodeTable = nodeTable;
	}
	
	@Override
	public void run() {
		List<String> data = nodeTable.getTableModel().getRow(nodeTable.getSelectedRow());
		window.onNodeSelect(dbusTree, data.get(0));
	}

}
