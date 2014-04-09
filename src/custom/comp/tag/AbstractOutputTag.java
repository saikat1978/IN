package custom.comp.tag;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import custom.comp.base.AttributeConstants;



public abstract class AbstractOutputTag extends AbstractCustomTag {

	private String template = null;
	private String componentId = null; // component ID
	private String styleClass = null;
	private String disabled = null;
	private String size = null;
	
	public void release() {
		super.release();		
		componentId = null;
		template = null;
		styleClass = null;
		disabled = null;
		size = null;
	}

	public String getRendererType() { // NOPMD by NL65921 on 15-9-10 10:30
		return null;
	}

	public void setProperty(UIComponent component, String tagValue, String tagName) {
		if (tagValue != null) {
			if (isValueReference(tagValue)) {
				FacesContext context = FacesContext.getCurrentInstance();
				Application app = context.getApplication();
				ValueBinding vb = app.createValueBinding(tagValue);
				component.setValueBinding(tagName, vb);
			} else {
				component.getAttributes().put(tagName, tagValue);
			}
		}
	}	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);	
		setProperty(component, template, AttributeConstants.TEMPLATE);
		setProperty(component, componentId, AttributeConstants.COMPONENT_ID);
		setProperty(component, styleClass, AttributeConstants.STYLE_CLASS);
		setProperty(component, disabled, AttributeConstants.DISABLED);
		setProperty(component, size, AttributeConstants.SIZE);
		setProperty(component, getId(), AttributeConstants.REFERENCE_ID);
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	public void setSize(String size) {
		this.size = size;
	}

}
