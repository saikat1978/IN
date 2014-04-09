package custom.comp.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;

public class ResponseHelper {
	/** logger */
	private static final Log LOG = LogFactory.getLog(ResponseHelper.class);
	
	/** current context */
	private static ThreadLocal currentResponseContext = new ThreadLocal();
	
	private static ThreadLocal contextValues = new ThreadLocal();

	private ResponseHelper() {
		super(); // not used
	}
	
	/**
	 * Initialize Velocity
	 */
	static {
		try {
			System.out.println("inside response handler static section");
			/*Properties properties = new Properties();
			properties.setProperty("resource.loader", "custom");
			properties.setProperty("directive.foreach.counter.initial.value", "0");
			properties.setProperty("custom.resource.loader.class", CustomResourceLoader.class.getName());
			Velocity.init(properties);*/
		} catch (Exception e) {
			LOG.error("Exception while initializing velocity framework", e);
		}
	}
	
	/**
	 * Get the current context
	 * @return
	 */
	private static ResponseContext getCurrentResponseContext() {
		return (ResponseContext)currentResponseContext.get();
	}
	
	/**
	 * Get the shared context values
	 * @return
	 */
	private static Map getContextValues() {
		return (Map)contextValues.get();
	}
	
	/**
	 * set the current context
	 * @param rc
	 */
	public static void setCurrentResponseContext(ResponseContext rc) {
		currentResponseContext.set(rc);
	}
	
	/**
	 * Creates a new context with the current context as its parent and then set the new
	 * context as current
	 * @param label
	 * @param fc
	 * @return
	 */
	public static ResponseContext createContext(String label, FacesContext fc) {
		// create custom response writer
		ResponseWriter writer = fc.getResponseWriter();
		CustomResponseWriter crw = new CustomResponseWriter(writer);
		fc.setResponseWriter(crw);
		
		ResponseContext parentContext = getCurrentResponseContext();	
		ResponseContext rc = new ResponseContext(label, parentContext);
		setCurrentResponseContext(rc);

		if (parentContext == null) {
			// this is a root element
			Map sharedValues = new HashMap();		
			//sharedValues.put(AttributeConstants.BUNDLE, ApplicationContext.getInstance().getResourceBundle());
			//sharedValues.put(AttributeConstants.NAMESPACE, UIComponentUtils.getNamespace(fc));
			sharedValues.put(AttributeConstants.IE6, UIComponentUtils.isIE6(fc)?Boolean.TRUE:Boolean.FALSE);
			contextValues.set(sharedValues);
		}	
		rc.getContext().putAll(getContextValues());

		return rc;
	}
	
	/**
	 * Returns the context with the specified label. It will first look at the current context and if
	 * this is not a match it will check the parents until a match is found
	 * @param label
	 * @return
	 */
	public static ResponseContext getContext(String label) {
		ResponseContext rc = getCurrentResponseContext();
		while (rc != null) {
			if (rc.getLabel().equals(label)) {
				return rc;
			}
			rc = rc.getParentContext();
		}
		return rc;
	}
	
	/**
	 * Helper method that concatenates two Strings even if one if them is null
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static String concat(String s1, String s2) {
		if (s1 == null) {
			return s2;
		}
		if (s2 == null) {
			return s1;
		}
		return new StringBuffer(s1).append(s2).toString(); // faster than +
	}
	
	/**
	 * Helper method that concatenates two Strings when both are not null
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static String concatNotNull(String s1, String s2) {
		return new StringBuffer(s1).append(s2).toString(); // faster than +
	}
	
	/**
	 * Renders the supplied template using the parameters set on the supplied context. 
	 * @param template
	 * @param rc
	 * @param fc
	 */
	public static void render(String template, ResponseContext rc, FacesContext fc) { // NOPMD by NL65921 on 17-9-10 9:06
		if (template == null) {
			throw new ComponentConfigurationException("No template specified for " + rc.getLabel());
		}
		//if (LOG.isDebugEnabled()) {
			// expensive string concatenation
			//LOG.debug("Using template: " + template + " for component: " + rc.getLabel() + "/" + rc.get("id"));
		//}
		
		// first check if any children have written to the custom response writer
		// only standard JSF components will have written to the response writer
		// get all contents and add it to the current BODY
		ResponseWriter writer = fc.getResponseWriter();
		if (writer instanceof CustomResponseWriter) {
			// should be the case, ignore otherwise
			CustomResponseWriter crw = (CustomResponseWriter)writer;
			String contents = strip(crw.toString());
			rc.set(ResponseContext.BODY, concat((String)rc.get(ResponseContext.BODY),contents));
			fc.setResponseWriter(crw.getOriginalResponseWriter());
		}
		
		try {
			ResponseContext parentContext = rc.getParentContext();
			
			// do the actual rendering and store the result in a String
			String content = strip(rc.render(template));
			
			// if this is an AJAX request, we need to do some special stuff, 
			// otherwise we will either have a parent which means we are current rendering a child
			// of another custom UI component, or we have no parent and we either are a child of a standard
			// JSF component or are the top component. In both these cases with no parent we will need to write the
			// rendered content to the response.
			// When we do have a parent, we do not write to the response, but make the rendered content of this
			// component available to the parent context (publish), so the parent can do custom layouting.
			
			String id = (String)rc.get(AttributeConstants.REFERENCE_ID);
			if (parentContext != null) {
				// publish in four ways:
				// under specific label 
				// as list item
				// append to generic BODY label
				// using reference ID
				// under component ID (cid) if any was specified
				if (!rc.excludeFromBody()) {
					parentContext.set(ResponseContext.BODY, concat((String)parentContext.get(ResponseContext.BODY),content));
				}
				parentContext.set(rc.getLabel(), content);
				
				if (id != null) {
					parentContext.set(id, content);
					parentContext.set(concatNotNull(id,ResponseContext.CONTEXT), rc.getContext());
				}
				String componentId = (String)rc.get(AttributeConstants.COMPONENT_ID);
				if (componentId != null) {
					parentContext.set(componentId, content);
					parentContext.set(concatNotNull(componentId, ResponseContext.CONTEXT), rc.getContext());
				}
				String listId = concatNotNull(rc.getLabel(), ResponseContext.LIST);
				List items = (List)parentContext.get(listId);
				if (items == null) {
					items = new ArrayList();
					parentContext.set(listId, items);
				}
				items.add(content);
			} else if (content != null) {
				// no parent, so write to response
				fc.getResponseWriter().write(content);
			}
		} catch (Exception e) {
			LOG.error("Exception while rendering content for: " + rc, e);
		}
		setCurrentResponseContext(rc.getParentContext());
	}
	
	/**
	 * String whitespace from String, returns null if String is only whitespace.
	 * @param s
	 * @return
	 */
	private static String strip(String s) {
		// strip spaces from string. if string only contains whitespace, return null so we can use
		// simple velocity if conditions
		if (s != null) {
			String st = s.trim();
			if (st.length() == 0) {
				return null;
			}
			return st;
		}
		return null;
	}

}
