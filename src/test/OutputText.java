package test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import custom.comp.base.AttributeConstants;
import custom.comp.base.UICustomComponent;
import custom.comp.base.UICustomOutput;

public class OutputText extends UICustomOutput implements UICustomComponent, AttributeConstants, NamingContainer {

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "output-text2";
	}
	
	@Override
	protected void setTemplateParameters(FacesContext facesContext)
			throws IOException {
		String text = (String) getAttributes().get(TEXT);
		String inline = (String) getAttributes().get(INLINE);
		String template = (String) getAttributes().get(TEMPLATE);
		/*rc.set(TEXT, text);
		rc.set(INLINE, inline);
		rc.set(TEMPLATE, template);*/
	}
	
	
	

}
