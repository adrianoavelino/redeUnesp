package br.com.unesp.converter;

import br.com.unesp.model.Usuario;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "usuarioConverter")
public class UsuarioConverter implements Converter {

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
            Usuario usuario = (Usuario) value;
            if (usuario.getId() != null) {
                this.addAtribute(component, usuario);
                return usuario.getId().toString();
            }
        }
        return null;
    }

    private Map<String, Object> getAttributeForm(UIComponent component) {
        return component.getAttributes();
    }

    private void addAtribute(UIComponent component, Usuario usuario) {
        this.getAttributeForm(component).put(usuario.getId().toString(), usuario);
    }

}
