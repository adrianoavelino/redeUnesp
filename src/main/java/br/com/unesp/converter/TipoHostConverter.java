package br.com.unesp.converter;

import br.com.unesp.model.TipoHost;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tipoHostConverter")
public class TipoHostConverter implements Converter {

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
            TipoHost tipoHost = (TipoHost) value;
            if (tipoHost.getId() != null) {
                this.addAtribute(component, tipoHost);
                return tipoHost.getId().toString();
            }
        }
        return null;
    }

    private Map<String, Object> getAttributeForm(UIComponent component) {
        return component.getAttributes();
    }

    private void addAtribute(UIComponent component, TipoHost tipoHost) {
        this.getAttributeForm(component).put(tipoHost.getId().toString(), tipoHost);
    }

}
