package custom.comp.base;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.*;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.EscapeTool;

public class ResponseContext implements Serializable {
private static final long serialVersionUID = 8380346106753950230L;
	
	/** cache for all already loaded templates */
	private static Map templates = new HashMap();

	/** label for body */
	public final static String BODY = "body";
	
	/** postfix for lists */ 
	public final static String LIST = "List";
	
	/** postfix for context */
	public final static String CONTEXT = "Context";

	/** parent context */
	private ResponseContext parentContext = null;
		
	/** parameters put in the context can be exposed to the parent **/
	final private Map exposedContext;
	
	/** label of the context */
	private String label = null;
	
	/** if true, the rendered content will not add itself to the BODY */
	private boolean excludeFromBody = false; // NOPMD by NL65921 on 15-9-10 16:50
	
	private final static String ESCAPE_TOOL_LABEL = "escape";
	
	private final static EscapeTool ESCAPE_TOOL = new EscapeTool(); // let's hope this is thread safe
	
	/**
	 * Should the rendered content by excluded from the parent's body?
	 * @return
	 */
	public boolean excludeFromBody() {
		return excludeFromBody;
	}

	/**
	 * Set excludeFromBody
	 * @param excludeFromBody
	 */
	public void setExcludeFromBody(boolean excludeFromBody) {
		this.excludeFromBody = excludeFromBody;
	}

	/**
	 * Get label
	 * @return
	 */
	String getLabel() { // NOPMD by NL65921 on 15-9-10 16:50
		return label;
	}

	/**
	 * Set the label
	 * @param label
	 */
	void setLabel(String label) { // NOPMD by NL65921 on 15-9-10 16:50
		this.label = label;
	}

	/**
	 * Get the parent context
	 * @return
	 */
	public ResponseContext getParentContext() {
		return parentContext;
	}

	/**
	 * Sets the parent context
	 * @param parentContext
	 */
	void setParentContext(ResponseContext parentContext) { // NOPMD by NL65921 on 15-9-10 16:50
		this.parentContext = parentContext;
	}

	/**
	 * Creates a new ResponseContext
	 * @param label
	 * @param parentContext
	 */
	ResponseContext(String label, ResponseContext parentContext) {
		super();
		this.label = label;
		this.parentContext = parentContext;
		this.exposedContext = new HashMap();
	}
	
	/**
	 * Set an object on the context
	 * @param id
	 * @param o
	 */
	public void set(String id, Object o) {
		exposedContext.put(id, o);
	}

	/**
	 * Returns the template, either from a cache or retrieving it from Velocity (expensive)
	 * @param template
	 * @return
	 * @throws Exception
	 */
	private static Template getTemplate(String template) throws Exception { // NOPMD by NL65921 on 15-9-10 16:52
		Template t = (Template)templates.get(template);
		if (t == null) {
			t = Velocity.getTemplate(template);
			templates.put(template, t);
		}
		return t;
	}
	
	/**
	 * Renders the template with the parameters currently on the context
	 * @param template
	 * @return
	 * @throws Exception
	 */
	public String render(String template) throws Exception { // NOPMD by NL65921 on 15-9-10 16:52
		Template t = getTemplate(template);
		StringWriter sw = new StringWriter();
		VelocityContext vc = new VelocityContext(exposedContext);
		vc.put(ESCAPE_TOOL_LABEL, ESCAPE_TOOL);
		t.merge( vc, sw );	
		return sw.toString();
	}
			
	/**
	 * Get value from the context
	 * @param id
	 * @return
	 */
	public Object get(String id) {
		return exposedContext.get(id);
	}
	
	/**
	 * Create simple String representation
	 */
	public String toString() {
		if (parentContext != null) {
			return parentContext.toString() + " > " + label;
		}
		return "ROOT : " + label;
	}

	protected Map getContext() {
		return exposedContext;
	}
}
