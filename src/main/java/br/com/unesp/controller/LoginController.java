package br.com.unesp.controller;

import br.com.unesp.dao.LoginDao;
import br.com.unesp.model.Login;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "loginController")
@RequestScoped
public class LoginController {
    @EJB
    private LoginDao meuDao;

    public LoginController() {
    }
    
    public void salvar() {
        Login login1 = new Login();
        login1.setNome("adriano");
        login1.setEmail("adrianno@emailcomm");
        login1.setSenha("123");
        meuDao.create(login1);
        System.out.println("salvando ...");
    }
    
}
