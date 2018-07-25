package br.com.unesp.controller;

import br.com.unesp.dao.TipoHostDao;
import br.com.unesp.model.TipoHost;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Validators implements Serializable {

    @Inject
    private TipoHostDao dao;

    public void validateUniqueTipoHost(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String tipo = (String) value;
        int id = getIdComparator(context, component, value);
        TipoHost tipoTestado = new TipoHost(id, tipo);

        if (dao.isTipoHostDuplicado(tipoTestado)) {
            String m = "O tipo de host já está sendo usado";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, m, "");
            throw new ValidatorException(message);
        }
    }
    
    private int getIdComparator(FacesContext context, UIComponent component, Object value) {
        UIInput confirmComponent = (UIInput) component.getAttributes().get("id_comparator");
        String confirm = (String) confirmComponent.getSubmittedValue();
        int id;

        try {
            id = Integer.parseInt(confirm);
        } catch (NumberFormatException e) {
            id = 0;
        }   
        return id;
    }

}
