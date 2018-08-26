package br.com.unesp.controller;

import br.com.unesp.dao.UsuarioDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Usuario;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "usuarioController")
@RequestScoped
public class UsuarioController {

    @Inject
    private UsuarioDao dao;
    @Inject
    private FacesMessages message;
    private Usuario usuario = new Usuario();

    public UsuarioController() {
    }

    public UsuarioDao getDao() {
        return dao;
    }

    public void setDao(UsuarioDao dao) {
        this.dao = dao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void salvar() {
        if (usuario.getId() == null) {
            if (this.validar()) {
                dao.salvar(this.usuario);
                usuario = new Usuario();
                message.info("Cadastro realizado com sucesso!");
            }
        } else {
            if (this.validar()) {
                try {
                    dao.atualizar(this.usuario);
                    this.usuario = new Usuario();
                    message.info("Alterado com sucesso!");
                } catch (Exception ex) {
                    message.error("Erro ao alterar Usuario");
                }
            }

        }
    }

    public void deletar(ActionEvent evento) {
        usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
        try {
            dao.deletar(usuario);
            message.info("Usuario deletado com sucesso!");
            usuario = new Usuario();
        } catch (Exception ex) {
            message.error("Erro ao deletar usuario");
        }
    }

    public void editar(ActionEvent evento) {
        usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
    }

    public boolean validar() {
        boolean temErro = true;
        if (dao.isNomeDuplicado(usuario)) {
            message.error("O nome já está cadastrado");
            temErro = false;
        }
        if (dao.isMatriculaDuplicada(usuario)) {
            message.error("Matrícula já está cadastrada");
            temErro = false;
        }
        return temErro;
    }

}
