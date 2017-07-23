package Exception;

public class DBusExplorationException extends Exception {

	private static final long serialVersionUID = -1463126295133702339L;
	
	private final String message;
	
	public DBusExplorationException(String message) {
		this.message = message;
	}
	
	public String getMessage() { return message; }

}
