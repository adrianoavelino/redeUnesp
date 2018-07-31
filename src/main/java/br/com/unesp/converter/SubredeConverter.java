package br.com.unesp.converter;

import br.com.unesp.model.Subrede;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "subredeConverter")
public class SubredeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || !value.matches("\\d+")) {
            return null;
        }
        return this.getAttributeForm(component).get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Subrede subrede = (Subrede) value;
            if (subrede.getId() != null) {
                this.addAtribute(component, subrede);
                return subrede.getId().toString();
            }
        }
        return null;
    }

    private Map<String, Object> getAttributeForm(UIComponent component) {
        return component.getAttributes();
    }

    private void addAtribute(UIComponent component, Subrede subrede) {
        this.getAttributeForm(component).put(subrede.getId().toString(), subrede);
    }

}
