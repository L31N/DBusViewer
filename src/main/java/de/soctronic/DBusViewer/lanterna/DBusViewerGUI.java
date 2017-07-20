package de.soctronic.DBusViewer.lanterna;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.soctronic.DBusViewer.lanterna.MainWindow;

public class DBusViewerGUI {

	private WindowBasedTextGUI gui;
	private MainWindow mainWindow;

	public DBusViewerGUI() {
		try {
			Terminal term = new DefaultTerminalFactory().createTerminal();
			Screen screen = new TerminalScreen(term);
			gui = new MultiWindowTextGUI(screen);
			screen.startScreen();
		} catch (IOException ex) {
			System.err.println("could not initilize GUI: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void displayBusNames(List<String> busnames) {
		mainWindow = new MainWindow(busnames);
		gui.addWindowAndWait(mainWindow);
	}
}
