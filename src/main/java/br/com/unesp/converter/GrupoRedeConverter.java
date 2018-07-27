package br.com.unesp.converter;

import br.com.unesp.model.GrupoRede;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "grupoRedeConverter")
public class GrupoRedeConverter implements Converter {
    
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
            GrupoRede grupoRede = (GrupoRede) value;
            if (grupoRede.getId() != null) {
                this.addAtribute(component, grupoRede);
                return grupoRede.getId().toString();
            }
        }
        return null;
    }

    private Map<String, Object> getAttributeForm(UIComponent component) {
        return component.getAttributes();
    }

    private void addAtribute(UIComponent component, GrupoRede grupoRede) {
        this.getAttributeForm(component).put(grupoRede.getId().toString(), grupoRede);
    }    
}
