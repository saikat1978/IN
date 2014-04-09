package custom.comp.base;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;





public class UIComponentUtils {

	/**
	 * private constructor, no instances
	 */
	private UIComponentUtils() {
		//private constructor, no instances
	}
	
	private static String get(String attributeName, UICustomComponent component, Object globalSettings) {
		return (String)getObject(attributeName, component, globalSettings);
	}
	
	public static String getUrl(FacesContext facesContext) {
		RenderRequest request = (RenderRequest)facesContext.getExternalContext().getRequest();
		RenderResponse response = (RenderResponse)facesContext.getExternalContext().getResponse();
		return response.encodeURL(request.getContextPath());
	}
	
	public static String getNamespace(FacesContext facesContext) {
		RenderResponse response = (RenderResponse)facesContext.getExternalContext().getResponse();
		return response.getNamespace();
	}

	public static boolean isIE6(FacesContext context) {
		String ua = FacesUtils.getUserAgent(context); 
		/*if (ua != null && ua.indexOf("MSIE 6.0") > -1) {
			return true;
		}*/
		return false;
	}
	
	public static PortletSession getPortletSession(FacesContext facesContext) {
		return ((PortletRequest)facesContext.getExternalContext().getRequest()).getPortletSession();
	}
	
	private static Object getObject(String attributeName, UICustomComponent component, Object globalSettings) {
		Object attrValue = component.getAttributes().get(attributeName);
		
		return attrValue;
	}
	
	public static Boolean getBoolean(String attributeName, UICustomComponent component, boolean useGlobalSettings) {
		Object o = getObject(attributeName, component, useGlobalSettings);
		if (o != null) {
			if (o instanceof Boolean) {
				return (Boolean)o;
			} 
			
			if ("true".equals(o)) {
				return Boolean.TRUE;
			} else if ("false".equals(o)) {
				return Boolean.FALSE;
			} else {
				throw new ComponentProgrammerException("Attribute "+ attributeName +" with value " + o + "cannot be converted to a boolean");
			}
		}
		return Boolean.FALSE;
	}
	
	public static String get(String attributeName, UICustomComponent component, boolean useGlobalSettings) {
		if (useGlobalSettings) {
			return get(attributeName, component, null);
		}
		return get(attributeName, component, null);
	}
	
	public static Object getObject(String attributeName, UICustomComponent component, boolean useGlobalSettings) {
		if (useGlobalSettings) {
			return getObject(attributeName, component, null);
		}
		return getObject(attributeName, component, null);
	}

	public static Object getGlobalSettings() {
		/*try {
			return GlobalSettingsHelper.getGlobalSettings(ApplicationContext.getInstance().getGlobalSettingsPath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}*/
		return null;
	}
	
	/**
	 * Returns true if component is within a form
	 * @param component
	 * @return
	 */
	public static boolean isComponentWithinForm(UIComponent component) {
		boolean inForm = false;
		UIComponent parent = component.getParent();
		while (!inForm && parent != null) {
			if (parent instanceof javax.faces.component.UIForm) {
				inForm = true;
			}
			parent = parent.getParent();
		}
		return inForm;
	}
	
	/**
	 * <p>Return the {@link UIComponent} (if any) with the specified
	 * <code>id</code>, searching recursively starting at the specified
	 * <code>base</code>, and examining the base component itself, followed
	 * by examining all the base component's facets and children.
	 * Unlike findComponent method of {@link UIComponentBase}, which
	 * skips recursive scan each time it finds a {@link NamingContainer},
	 * this method examines all components, regardless of their namespace
	 * (assuming IDs are unique).
	 *
	 * @param base Base {@link UIComponent} from which to search
	 * @param id Component identifier to be matched
	 */
	public static UIComponent findComponent(UIComponent base, String id) {

		// Is the "base" component itself the match we are looking for?
		if (id.equals(base.getId())) {
			return base;
		}

		// Search through our facets and children
		UIComponent kid = null;
		UIComponent result = null;
		Iterator kids = base.getFacetsAndChildren();
		while (kids.hasNext() && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
				break;
			}
			result = findComponent(kid, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	public static UIComponent findComponentInRoot(String id) {
		UIComponent ret = null;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIComponent root = context.getViewRoot();
			ret = findComponent(root, id);
		}

		return ret;
	}

}
