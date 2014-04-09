package custom.comp.jsf;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import custom.comp.base.AttributeConstants;
import custom.comp.base.UIComponentTypes;
import custom.comp.base.UICustomComponent;



public class UIParam extends UIOutput implements AttributeConstants, NamingContainer, UICustomComponent {
	public void encodeBegin(FacesContext context) {
		//leave method empty, otherwise parent might render something
	}
	
	public void encodeEnd(FacesContext context) {
		// leave method empty, otherwise parent might render something
	}
	
	public String getType() {
		return UIComponentTypes.PARAM;
	}
	
	public Object getParamValue(){
		Object value = getAttributes().get(AttributeConstants.VALUE);
		if(value == null){
			Object defaultValue = getAttributes().get(AttributeConstants.DEFAULT_VALUE);
			if(defaultValue != null && !EMPTY_STRING.equals(defaultValue)){
				return defaultValue;
			}
		}
		return value;
	}
}
