package custom.comp.base;

public class ComponentException extends RuntimeException {
	/** generated serial version id */
	private static final long serialVersionUID = -7576802852614627371L;
	
	/** The message key for the construction of an Exception */
	final private String messageKey;
	
	/** The message arguments for the construction of an Exception */
	final private Object[] messageArgs;
	
	/** The root cause exception */
	final private Throwable rootCause;
	
	public Object[] getMessageArgs() {
		return messageArgs; // NOPMD by NL65921 on 17-9-10 9:01
	}

	public String getMessageKey() {
		return messageKey;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * ComponentException constuctor with: 
	 * @param message
	 * @param messageKey
	 * @param mArgs
	 * @param t
	 */
	public ComponentException(String message, String pMessageKey, Object[] mArgs, Throwable t) { // NOPMD by NL65921 on 17-9-10 9:01
		super(message);
		messageKey = pMessageKey; 
		messageArgs = mArgs;
		rootCause = t;
	}
	
	/**
	 * ComponentException constuctor with: 
	 * @param message
	 * @param messageKey
	 * @param mArgs
	 */
	public ComponentException (String message, String pMessageKey, Object[] mArgs) { // NOPMD by NL65921 on 17-9-10 9:01
		super(message);
		messageKey = pMessageKey; 
		messageArgs = mArgs;
		rootCause = null;
	}

}
