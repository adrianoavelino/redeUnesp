package br.com.unesp.controller;

import br.com.unesp.dao.VlanDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.GrupoRede;
import br.com.unesp.model.Vlan;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class VlanController {
    @Inject
    private VlanDao dao;
    @Inject
    private FacesMessages message;
    private Vlan vlan = new Vlan();
    private List<Vlan> listaVlan;

    public VlanController() {
    }

    public VlanDao getDao() {
        return dao;
    }

    public void setDao(VlanDao dao) {
        this.dao = dao;
    }

    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan) {
        this.vlan = vlan;
    }

    public List<Vlan> getListaVlan() {
        return listaVlan;
    }

    public void setListaVlan(List<Vlan> listaVlan) {
        this.listaVlan = listaVlan;
    }

    public void salvar() {
        if (vlan.getId() == null) {
            dao.salvar(this.vlan);
            vlan = new Vlan();
            message.info("Cadastro realizado com sucesso!");
        } else {
            try {
                dao.atualizar(vlan);
                vlan = new Vlan();
                message.info("Alterado com sucesso!");
            } catch (Exception ex) {
                message.error("Erro ao alterar vlan");
            }
        }
    }

    public void deletar(ActionEvent evento) {
        vlan = (Vlan) evento.getComponent().getAttributes().get("vlanSelecionada");
        try {
            dao.deletar(vlan);
            message.info("Deletada com sucesso!");
        } catch (Exception ex) {
            message.error("Erro ao deletar vlan");
        }
    }

    public void editar(ActionEvent evento) {
        vlan = (Vlan) evento.getComponent().getAttributes().get("vlanSelecionada");
    }

}
