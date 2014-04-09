package test;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public class OutputTag extends UIComponentTag {

	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "test.UIOutput";
	}

	@Override
	public String getRendererType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setProperties(UIComponent component) {
		// TODO Auto-generated method stub
		super.setProperties(component);
		
		// set hellomsg
	    if (message != null) 
	    { 
	      if (isValueReference(message))
	      {
	        FacesContext context = FacesContext.getCurrentInstance();
	        Application app = context.getApplication();
	        ValueBinding vb = app.createValueBinding(message);
	        component.setValueBinding("message", vb);                  
	      }
	      else 
	        component.getAttributes().put("message", message);
	    }          
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		message = null;
	}
}
