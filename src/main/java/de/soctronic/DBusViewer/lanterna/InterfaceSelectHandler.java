package de.soctronic.DBusViewer.lanterna;

import java.util.List;

import com.googlecode.lanterna.gui2.table.Table;

import de.soctronic.DBusViewer.DBusNode;
import de.soctronic.DBusViewer.DBusTree;

public class InterfaceSelectHandler implements Runnable {

	private MainWindow window;
	private DBusNode node;
	private Table<String> table;
	
	public InterfaceSelectHandler(MainWindow window, DBusNode node, Table<String> table) {
		this.window = window;
		this.node = node;
		this.table = table;
	}
	
	@Override
	public void run() {
		List<String> data = table.getTableModel().getRow(table.getSelectedRow());
		window.onInterfaceSelect(node, data.get(0));	
	}

}
