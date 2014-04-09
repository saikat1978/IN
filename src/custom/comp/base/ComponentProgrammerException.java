package custom.comp.base;

public class ComponentProgrammerException extends ComponentException {
	/**
	 * ComponentConfigurationException constuctor with: 
	 */
	public ComponentProgrammerException(String message) {
		super(message, "programmerError", null);
	}

	/** generated erial version id */
	private static final long serialVersionUID = -7576802852614627371L;
	
	/**
	 * ComponentConfigurationException constuctor with: 
	 * @param message
	 * @param messageKey
	 * @param mArgs
	 * @param t
	 */
	public ComponentProgrammerException(String message, String messageKey, Object[] mArgs, Throwable t) {
		super(message, messageKey, mArgs, t);
		
	}

	/**
	 * ComponentConfigurationException constuctor with: 
	 * @param message
	 * @param t
	 */
	public ComponentProgrammerException(String message, Throwable t) {
		super(message, "programmerError", null, t);
		
	}

	/**
	 * ComponentConfigurationException constuctor with: 
	 * @param message
	 * @param messageKey
	 * @param mArgs
	 */
	public ComponentProgrammerException (String message, String messageKey, Object[] mArgs) {
		super(message, messageKey, mArgs);
		
	}

}
