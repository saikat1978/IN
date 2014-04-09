package custom.comp.base;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Logger;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public abstract class UICustomOutput extends UIOutput implements AttributeConstants, NamingContainer, UICustomComponent {

	protected ResponseContext rc = null;

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponentBase#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext context) { // NOPMD by NL65921 on 15-9-10 10:55
		// output components have nothing to decode in general
	}

	public final void encodeBegin(FacesContext facesContext) throws IOException {
		if(isRendered()) {
			rc = ResponseHelper.createContext(getType(), facesContext);
						
			setTemplateParameter(STYLE_CLASS, true);
			setTemplateParameter(COMPONENT_ID, false);
			setTemplateParameter(DISABLED, false);
			setTemplateParameter(SIZE, true);
			setTemplateParameter(REFERENCE_ID, false);
			
			rc.set(ID, getClientId(facesContext));
			
			encodeBeginImpl(facesContext);
		}
	}

	public final void encodeEnd(FacesContext facesContext) throws IOException {
		if(isRendered()){
			encodeEndImpl(facesContext);
			setTemplateParameters(facesContext);
			
			//ResponseHelper.render(get(TEMPLATE, true), rc, facesContext);
			//ResponseHelper.render(get(TEMPLATE, false), rc, facesContext);
			replaceResponseHelper(facesContext);
			rc = null; // ensure garbage collection, velocity context can grow large for some reason
		}
	}
	
	private void replaceResponseHelper(FacesContext context) {
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String myTemplatePath = servletContext.getRealPath("/WEB-INF/templates");
		
		System.out.println("template path " + myTemplatePath);
		
		Properties p = new Properties();
		p.setProperty( "file.resource.loader.path", myTemplatePath);
		try {
			Velocity.init(p);
			Template template = Velocity.getTemplate( "output.vm" ); // rc.get
			VelocityContext velocityContext = new VelocityContext();
			
			velocityContext.put("message", getAttributes().get(TEXT));
			StringWriter stringWriter = new StringWriter();
			
			template.merge(velocityContext, stringWriter);
			System.out.println(stringWriter.toString());
			
			context.getResponseWriter().write(stringWriter.toString());
			context.getResponseWriter().flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected String get(String attributeName, boolean useGlobalSettings) {
		return UIComponentUtils.get(attributeName, this, useGlobalSettings);
	}

	protected Boolean getBoolean(String attributeName, boolean useGlobalSettings) {
		return UIComponentUtils.getBoolean(attributeName, this, useGlobalSettings);
	}

	protected Object getObject(String attributeName, boolean useGlobalSettings) {
		return UIComponentUtils.getObject(attributeName, this, useGlobalSettings);
	}
	
	/**
	 * Override if something needs to be done during encodeBegin phase
	 * @param facesContext
	 * @throws IOException
	 */
	protected void encodeBeginImpl(FacesContext facesContext) throws IOException { // NOPMD by NL65921 on 15-9-10 10:55
		// children can override
	}
	
	/**
	 * Override if something needs to be done during encodeEnd phase
	 * @param facesContext
	 * @throws IOException
	 */
	protected void encodeEndImpl(FacesContext facesContext) throws IOException { // NOPMD by NL65921 on 15-9-10 10:55
		// children can override
	}
	
	/**
	 * Override to set parameters for the template
	 * @param facesContext
	 * @throws IOException
	 */
	protected void setTemplateParameters(FacesContext facesContext) throws IOException { // NOPMD by NL65921 on 15-9-10 10:55
		// children can override
	}
	
	protected void setTemplateParameter(String key, boolean useGlobalSettings) {
		rc.set(key, get(key, useGlobalSettings));
	}
	
	protected void setBooleanTemplateParameter(String key, boolean useGlobalSettings) {
		rc.set(key, getBoolean(key, useGlobalSettings));
	}

}
