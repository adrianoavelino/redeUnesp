package br.com.unesp.controller;

import br.com.unesp.dao.TipoHostDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.TipoHost;
import java.util.List;
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
                message.error("Erro ao cadastrar tipo de host");
            }
        }
    }
    
    public void editar(ActionEvent evento) {
        tipoHost = (TipoHost) evento.getComponent().getAttributes().get("tipoHostSelecoinado");
    }
    
    public void deletar(ActionEvent evento) {
        tipoHost = (TipoHost) evento.getComponent().getAttributes().get("tipoHostSelecoinado");
        try {
            dao.deletar(tipoHost);
            message.info("Deletado com sucesso");
        } catch (Exception ex) {
            message.error("Erro ao deletar tipo do host");
        }
    }
}
