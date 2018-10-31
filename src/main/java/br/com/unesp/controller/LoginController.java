package br.com.unesp.controller;

import br.com.unesp.dao.LoginDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Login;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

@Named(value = "loginController")
@RequestScoped
public class LoginController implements Serializable {

    @Inject
    private LoginDao loginDao;
    private Login login = new Login();
    @Inject
    private FacesMessages message;
    private String confirmarSenha;

    public LoginController() {
    }

    public LoginDao getLoginDao() {
        return loginDao;
    }

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public void salvar() {
        if (!this.login.getSenha().equals(this.confirmarSenha)) {
            message.error("O campo senha e confirmar senha são diferentes");
            return;
        }
        if (this.login.getId() == null) {
            if (this.login.getSenha().isEmpty()) {
                message.error("Preencha o campo senha");
                return;
            }
            loginDao.salvar(this.login);
            this.login = new Login();
            message.info("Login salvo com sucesso!");
        } else {
            try {
                loginDao.atualizar(login);
                this.login = new Login();
                message.info("Login atualizado com sucesso!");
            } catch (Exception ex) {
                printStackTrace(ex);
                message.error("Erro ao atualizar Login");
            }
        }
    }

    public void deletar(ActionEvent evento) {
        login = (Login) evento.getComponent().getAttributes().get("loginSelecionado");
        try {
            loginDao.deletar(login);
            message.info("Login deletado com sucesso!");
            login = new Login();
        } catch (Exception ex) {
            System.out.println("Erro ao deletar login: " + ex);
        }
    }

    public void editar(ActionEvent evento) {
        login = (Login) evento.getComponent().getAttributes().get("loginSelecionado");
    }

    public String autenticar() {
        return "app/index?faces-redirect=true";
    }

}
