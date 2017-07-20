package de.soctronic.DBusViewer;

import java.util.HashMap;
import java.util.Map;

public class DBusTree {
	String busname;
	private Map<String, DBusNode> nodes;

	public DBusTree(String busname) {
		this.busname = busname;
		nodes = new HashMap<String, DBusNode>();
	}
	
	public DBusTree(String busname, Map<String, DBusNode> nodes) {
		this.busname = busname;
		this.nodes = nodes;
	}

	public Map<String, DBusNode> getNodes() {
		return nodes;
	}

	public void addNode(DBusNode node) {
		nodes.put(node.getObjectPath(), node);
	}
}
