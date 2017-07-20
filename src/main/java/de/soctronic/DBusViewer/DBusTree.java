package de.soctronic.DBusViewer;

import java.util.Map;

public class DBusTree {
	private Map<String, DBusNode> nodes;

	public DBusTree(Map<String, DBusNode> nodes) {
		this.nodes = nodes;
	}

	public Map<String, DBusNode> getNodes() {
		return nodes;
	}

	public void addNode(DBusNode node) {
		nodes.put(node.getObjectPath(), node);
	}
}
