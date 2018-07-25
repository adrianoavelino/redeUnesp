package br.com.unesp.controller;

import br.com.unesp.dao.TipoHostDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.GrupoRede;
import br.com.unesp.model.TipoHost;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "tipoHostController")
@RequestScoped
public class TipoHostController {

    @Inject
    private TipoHostDao dao;
    private TipoHost tipoHost = new TipoHost();
    @Inject
    private FacesMessages message;
    private List<TipoHost> tiposHosts;

    public TipoHostDao getDao() {
        return dao;
    }

    public void setDao(TipoHostDao dao) {
        this.dao = dao;
    }

    public TipoHost getTipoHost() {
        return tipoHost;
    }

    public void setTipoHost(TipoHost tipoHost) {
        this.tipoHost = tipoHost;
    }

    public TipoHostController() {
    }

    public List<TipoHost> getTiposHosts() {
        return tiposHosts;
    }

    public void setTiposHosts(List<TipoHost> tiposHosts) {
        this.tiposHosts = tiposHosts;
    }

    public void salvar() {
        if (tipoHost.getId() == null) {
            dao.salvar(this.tipoHost);
            tipoHost = new TipoHost();
            message.info("Cadastrado com sucesso!");
        } else {
       try {
            dao.atualizar(tipoHost);
            tipoHost = new TipoHost();
            message.info("Alterado com sucesso!");
        } catch (Exception ex) {
            System.out.println("Erro ao alterar");
        }
        }
    }

    public void listar() {
        try {
            tiposHosts = dao.listar();
            for (TipoHost tiposHost : tiposHosts) {
                System.out.println(tiposHost.getTipo());
            }
        } catch (Exception ex) {
            System.out.println("Erro ao listar");
        }
        System.out.println("Litsar ...");
    }

    public void editar(ActionEvent evento) {
        tipoHost = (TipoHost) evento.getComponent().getAttributes().get("tipoHostSelecoinado");
        System.out.println("Editar" + tipoHost);
    }

    public void deletar(ActionEvent evento) {
        tipoHost = (TipoHost) evento.getComponent().getAttributes().get("tipoHostSelecoinado");
        try {
            dao.deletar(tipoHost);
        } catch (Exception ex) {
            System.out.println("Erro ao deletar tipo do host");
        }
        System.out.println("Deletar" + tipoHost);
    }
}
