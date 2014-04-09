package custom.comp.base;

public class ComponentConfigurationException extends ComponentException {
	/**
	 * ComponentConfigurationException constuctor with: 
	 */
	public ComponentConfigurationException(String message) {
		super(message, "configurationProblem", null);
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
	public ComponentConfigurationException(String message, String messageKey, Object[] mArgs, Throwable t) {
		super(message, messageKey, mArgs, t);
		
	}
	
	/**
	 * ComponentConfigurationException constuctor with: 
	 * @param message
	 * @param t
	 */
	public ComponentConfigurationException(String message, Throwable t) {
		super(message, "configurationProblem", null, t);
		
	}
	
	/**
	 * ComponentConfigurationException constuctor with: 
	 * @param message
	 * @param messageKey
	 * @param mArgs
	 */
	public ComponentConfigurationException (String message, String messageKey, Object[] mArgs) {
		super(message, messageKey, mArgs);
		
	}

}
