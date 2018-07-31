package br.com.unesp.converter;

import br.com.unesp.model.Rede;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "redeConverter")
public class RedeConverter implements Converter {

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
            Rede rede = (Rede) value;
            if (rede.getId() != null) {
                this.addAtribute(component, rede);
                return rede.getId().toString();
            }
        }
        return null;
    }

    private Map<String, Object> getAttributeForm(UIComponent component) {
        return component.getAttributes();
    }

    private void addAtribute(UIComponent component, Rede rede) {
        this.getAttributeForm(component).put(rede.getId().toString(), rede);
    }

}
