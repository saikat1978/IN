package test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Logger;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class UIOutput extends UIComponentBase {

	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return "output-text";
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeBegin(context);
		ResponseWriter writer = context.getResponseWriter();
		String value = (String) getAttributes().get("message");
		if (value != null) {
			//writer.write(value);
			ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
			String myTemplatePath = servletContext.getRealPath("/WEB-INF/templates");

			Properties p = new Properties();
			p.setProperty( "file.resource.loader.path", myTemplatePath);
			try {
				Velocity.init(p);
				Template template = Velocity.getTemplate( "output.vm" );
				VelocityContext velocityContext = new VelocityContext();
				velocityContext.put("message", value);
				StringWriter stringWriter = new StringWriter();
				template.merge(velocityContext, stringWriter);
				Logger.getAnonymousLogger().info(stringWriter.toString());
				writer.write(stringWriter.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
	}

}
