package custom.comp.tag;

import javax.faces.component.UIComponent;

import custom.comp.base.AttributeConstants;
import custom.comp.base.UIComponentTypes;

public class ParamTag extends AbstractCustomTag {
	private String value = null;
	private String defaultValue = null;
	
	public String getComponentType() {
		return UIComponentTypes.PARAM;
	}

	public void release() {
		super.release();	
		value = null;
		defaultValue = null;
	}

	protected void setProperties(UIComponent component) {
		super.setProperties(component);	
		setProperty(component, value, AttributeConstants.VALUE);
		setProperty(component, defaultValue, AttributeConstants.DEFAULT_VALUE);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
