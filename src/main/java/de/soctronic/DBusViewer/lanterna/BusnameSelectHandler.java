package de.soctronic.DBusViewer.lanterna;

import java.util.List;

import com.googlecode.lanterna.gui2.table.Table;

public class BusnameSelectHandler implements Runnable {
	
	private MainWindow window;
	private Table<String> table;
	
	public BusnameSelectHandler(MainWindow window, Table<String> table) {
		this.window = window;
		this.table = table;
	}

	public void run() {
		List<String> data = table.getTableModel().getRow(table.getSelectedRow());
		window.onBusnameSelect(data.get(0));
	}
}
