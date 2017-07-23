package de.soctronic.DBusViewer.DBus;

import java.rmi.activation.UnknownObjectException;

public enum DBusType {
	
	INT16("Int16", "n"),
	UINT16("UInt16", "q"),
	INT32("Int32", "i"),
	UINT32("UInt32", "u"),
	INT64("Int64", "x"),
	UINT64("UInt64", "t"),
	DOUBLE("Double", "d"),
	BYTE("Byte", "y"),
	STRING("String", "s"),
	SIGNATURE("Signature", "g"),
	OBJECT_PATH("ObjectPath", "o"),
	BOOLEAN("Boolean", "b"),
	ARRAY("Array", "a"),
	STRUCT("Struct", "()"),
	DICT("Dict", "{}"),
	VARIANT("Variant", "v"),
	UNKNOWN("Unknown", "");
	
	private String str;
	private String dbusStr;
	
	private String originalStr;
	
	private DBusType(String str, String dbusStr) {
		this.str = str;
		this.dbusStr = dbusStr;
		
		this.originalStr = "";
	}
	
	private void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}
	

	public String toString() {
		if (this != UNKNOWN) {
			return this.str;
		} else {
			return originalStr;
		}
	}
	
	public static DBusType createFromDBusIdentifier(String dbusIdentifier) {
		for (DBusType type : DBusType.values()) {
			if (type.dbusStr.equalsIgnoreCase(dbusIdentifier)) {
				return type;
			}
		}
		DBusType unkown = UNKNOWN;
		unkown.setOriginalStr(dbusIdentifier);
		return unkown;
	}
}
