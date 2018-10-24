package br.com.unesp.controller;

import br.com.unesp.model.Login;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "autenticarController")
@SessionScoped
public class AutenticarController implements Serializable {
    
    private Login login = new Login();

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    public String autenticar() {
        ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
        external.getSessionMap().put("user", new Login(login.getNome(), login.getSenha(), login.getSenha()));        
        return "app/index?faces-redirect=true";
    }
        
    
}
