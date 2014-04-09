package custom.comp.tag;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public abstract class AbstractCustomTag extends UIComponentTag {

	public String getRendererType() { // NOPMD by NL65921 on 15-9-10 10:29
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

		
}
