package br.com.unesp.converter;

import br.com.unesp.model.Ipv6;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "ipv6Converter")
public class Ipv6Converter implements Converter {
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
            Ipv6 ipv6 = (Ipv6) value;
            if (ipv6.getId() != null) {
                this.addAtribute(component, ipv6);
                return ipv6.getId().toString();
            }
        }
        return null;
    }

    private Map<String, Object> getAttributeForm(UIComponent component) {
        return component.getAttributes();
    }

    private void addAtribute(UIComponent component, Ipv6 ipv6) {
        this.getAttributeForm(component).put(ipv6.getId().toString(), ipv6);
    }
    
    
}
