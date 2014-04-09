package custom.comp.base;

import javax.faces.context.FacesContext;
/*import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
*/


import com.ibm.faces.portlet.httpbridge.PortletRequestWrapper;
import com.ibm.faces.portlet.httpbridge.RenderRequestWrapper;
//import com.ibm.wps.pe.pc.legacy.impl.PortletRequestImpl;
import com.ibm.faces.portlet.httpbridge.PortletResponseWrapper;

public class FacesUtils {
	/** logger */
	//private static final Log logger = LogFactory.getLog(FacesUtils.class);
	
	/** request header key that contains the user agent string (the browser identification */
	private final static String USER_AGENT = "user-agent"; //$NON-NLS-1$
		
	/**
	 * Returns the original unwrapped request. Normally not necessary, but WCM for example does need it.
	 * @param context
	 * @return
	 */
	public static PortletRequestWrapper getUnwrappedPortletRequest(FacesContext context) {
		//PortletRequestWrapper requestWrapper = (PortletRequestWrapper) context.getExternalContext().getRequest();
		RenderRequestWrapper requestWrapper = (RenderRequestWrapper) context.getExternalContext().getRequest();
		return requestWrapper;
	}
	
	/**
	 * Returns the user-agent string sent by the clients browser
	 * @param context
	 * @return
	 */
	public static String getUserAgent(FacesContext context) {
		/*PortletRequest pri = getUnwrappedPortletRequest(context);
		return pri.getProperty(USER_AGENT);*/
		return "MSIE 6";
	}

	
	/**
	 * Returns the original unwrapped response. Normally not necessary, but WCM for example does need it.
	 * @param context
	 * @return
	 */
	/*public static PortletResponseWrapper getUnwrappedPortletResponse(FacesContext context) {
		PortletResponseWrapper responseWrapper = (PortletResponseWrapper) context.getExternalContext().getResponse();
		return responseWrapper;
	}*/
	
	/**
	 * Returns the portlet request (a wrapped version, JSF wraps the original request, see getUnwrappedPortletRequest)
	 * @param context
	 * @return
	 */
	public static Object getPortletRequest(FacesContext context) {
		return context.getExternalContext().getRequest();
	}
	
	/**
	 * Returns the portlet response (a wrapped version, JSF wraps the original response, see getUnwrappedPortletResponse)
	 * @param context
	 * @return
	 */
	public static Object getPortletResponse(FacesContext context) {
		return context.getExternalContext().getResponse();
	}

}
