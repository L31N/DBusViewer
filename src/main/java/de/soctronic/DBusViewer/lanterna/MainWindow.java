package de.soctronic.DBusViewer.lanterna;

import java.util.List;

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

import de.soctronic.DBusViewer.DBusExplorer;

public class MainWindow extends BasicWindow {

	private DBusExplorer dbusExplorer;

	// private Table<String> table;
	// private Panel mainPanel;
	// private Panel tablePanel;
	// private Button exitButton;

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
		Panel mainPanel = new Panel();
		mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

		Panel tablePanel = new Panel();

		Button exitButton = new Button("Exit", new Runnable() {
			public void run() {
				MainWindow.this.close();
			}
		});

		// table = new Table<String>("available bus names");
		final Table table = new Table<String>("Available Bus Names");

		List<String> busnames = dbusExplorer.discoverBusNames();
		for (String name : busnames) {
			table.getTableModel().addRow(name);
		}

		table.setSelectAction(new Runnable() {
			public void run() {
				List<String> data = table.getTableModel().getRow(table.getSelectedRow());
				System.out.println(data.get(0));
			}
		});

		tablePanel.addComponent(table);

		mainPanel.addComponent(table.withBorder(Borders.doubleLine()));
		mainPanel.addComponent(exitButton);

		this.setComponent(mainPanel);
	}
}
