package de.soctronic.DBusViewer;

import java.util.HashMap;
import java.util.Map;

public class DBusNode {
	private String objectPath;
	private Map<String, DBusInterface> interfaces;
	private Map<String, DBusNode> nodes;
	
	private DBusTree dbusTree;
	
	public DBusNode(String objectPath, DBusTree dbusTree) {
		this.objectPath = objectPath;
		this.interfaces = new HashMap<String, DBusInterface>();
		this.nodes = new HashMap<String, DBusNode>();
		
		this.dbusTree = dbusTree;
		
		System.out.println("created new node[" + objectPath + "]");
	}
	
	public DBusNode(String objectPath, Map<String, DBusInterface> interfaces, Map<String, DBusNode> nodes) {
		this.objectPath = objectPath;
		this.interfaces = interfaces;
		this.nodes = nodes;
	}

	public String getObjectPath() {
		return objectPath;
	}

	public Map<String, DBusInterface> getInterfaces() {
		return interfaces;
	}
	
	public Map<String, DBusNode> getNodes() {
		return nodes;
	}
	
	public void addInterface(DBusInterface iface) {
		this.interfaces.put(iface.getName(), iface);
	}
	
	public void addNode(DBusNode node) {
		this.nodes.put(node.getObjectPath(), node);
		this.dbusTree.addNode(node);
	}
	
	public DBusTree getDBusTree() {
		return this.dbusTree;
	}
}
