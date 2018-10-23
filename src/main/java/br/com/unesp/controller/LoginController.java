package br.com.unesp.controller;

import br.com.unesp.dao.LoginDao;
import br.com.unesp.model.Login;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    @EJB
    private LoginDao meuDao;
    private Login login = new Login();

    public LoginController() {
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    public void salvar() {
        Login login1 = new Login();
        login1.setNome("adriano");
        login1.setEmail("adrianno@emailcomm");
        login1.setSenha("123");
        meuDao.create(login1);
        System.out.println("salvando ...");
    }
    
    public String autenticar() {
        return "app/index?faces-redirect=true";
    }
    
}
