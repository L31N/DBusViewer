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

	private Screen screen;
	private WindowBasedTextGUI gui;
	private MainWindow mainWindow;

	public DBusViewerGUI() {
		try {
			Terminal term = new DefaultTerminalFactory().createTerminal();
			screen = new TerminalScreen(term);
			gui = new MultiWindowTextGUI(screen);
			screen.startScreen();
		} catch (IOException ex) {
			System.err.println("could not initilize GUI: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void show() {
		mainWindow = new MainWindow();
		gui.addWindowAndWait(mainWindow);
		close();
	}

	public void close() {
		try {
			screen.stopScreen();
		} catch(IOException ex) {
			System.err.println("could not stop screen: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
