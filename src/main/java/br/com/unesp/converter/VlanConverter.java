package br.com.unesp.converter;

import br.com.unesp.model.Vlan;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "vlanConverter")
public class VlanConverter implements Converter {

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
            Vlan vlan = (Vlan) value;
            if (vlan.getId() != null) {
                this.addAtribute(component, vlan);
                return vlan.getId().toString();
            }
        }
        return null;
    }

    private Map<String, Object> getAttributeForm(UIComponent component) {
        return component.getAttributes();
    }

    private void addAtribute(UIComponent component, Vlan vlan) {
        this.getAttributeForm(component).put(vlan.getId().toString(), vlan);
    }

}
