package de.soctronic.DBusViewer.lanterna;

import java.util.List;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;

public class MainWindow extends BasicWindow {
	
	private Table<String> table;
	private Panel mainPanel;
	private Panel tablePanel;
	private Button exitButton;
	
	public MainWindow(List<String> busnames) {
		super("DBusViewer 1.0.0-SNAPSHOT");
		
		mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        
        tablePanel = new Panel();
		
		exitButton = new Button("Exit", new Runnable() {
            public void run() {
                MainWindow.this.close();
            }
        });
		
		//table = new Table<String>("available bus names");
		table = new Table<String>("Available Bus Names");
		
		
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
