package br.com.unesp.controller;

import br.com.unesp.dao.HostDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Host;
import java.util.Collection;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "hostController")
@RequestScoped
public class HostController {

    @Inject
    private HostDao dao;
    @Inject
    private FacesMessages message;
    private Host host = new Host();
    private Collection<Host> hosts;

    public HostDao getDao() {
        return dao;
    }

    public void setDao(HostDao dao) {
        this.dao = dao;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Collection<Host> getHosts() {
        try {
            hosts = dao.listar();
        } catch (Exception ex) {
            message.error("Erro ao listar os hosts ");
        }
        return hosts;
    }

    public void setHosts(Collection<Host> hosts) {
        this.hosts = hosts;
    }

    public void salvar() {
        if (host.getId() == null) {
            dao.salvar(this.host);
            host = new Host();
            message.info("Cadastrado com sucesso!");
        } else {
            try {
                dao.atualizar(host);
                host = new Host();
                message.info("Alterado com sucesso!");
            } catch (Exception ex) {
                message.error("Erro ao alterar host");
            }
        }
    }

    public void deletar(ActionEvent evento) {
        host = (Host) evento.getComponent().getAttributes().get("hostSelecionado");
        try {
            dao.deletar(host);
        } catch (Exception ex) {
            message.error("Erro ao deletar tipo do host");
        }
    }

    public void editar(ActionEvent evento) {
        host = (Host) evento.getComponent().getAttributes().get("hostSelecionado");
    }
}
