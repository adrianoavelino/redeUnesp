package br.com.unesp.controller;

import br.com.unesp.dao.VlanDao;
import br.com.unesp.jsf.message.FacesMessages;
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
    private VlanDao vlanDao;
    @Inject
    private FacesMessages message;
    private Vlan vlan = new Vlan();
    private List<Vlan> listaDeIpsDaVlanSelecionada;

    public VlanController() {
    }

    public VlanDao getVlanDao() {
        return vlanDao;
    }

    public void setVlanDao(VlanDao vlanDao) {
        this.vlanDao = vlanDao;
    }

    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan) {
        this.vlan = vlan;
    }

    public List<Vlan> getListaDeIpsDaVlanSelecionada() {
        return listaDeIpsDaVlanSelecionada;
    }

    public void setListaDeIpsDaVlanSelecionada(List<Vlan> listaDeIpsDaVlanSelecionada) {
        this.listaDeIpsDaVlanSelecionada = listaDeIpsDaVlanSelecionada;
    }

    public void salvar() {
        if (vlan.getId() == null) {
            if (this.validar()) {
                vlanDao.salvar(this.vlan);
                vlan = new Vlan();
                message.info("Cadastro realizado com sucesso!");
            }
        } else {
            if (this.validar()) {
                try {
                    vlanDao.atualizar(this.vlan);
                    this.vlan = new Vlan();
                    message.info("Alterado com sucesso!");
                } catch (Exception ex) {
                    message.error("Erro ao alterar Vlan");
                }
            }

        }
    }

    public void deletar(ActionEvent evento) {
        vlan = (Vlan) evento.getComponent().getAttributes().get("vlanSelecionada");
        try {
            vlanDao.deletar(vlan);
            message.info("Deletada com sucesso!");
        } catch (Exception ex) {
            message.error("Erro ao deletar vlan");
        }
    }

    public void editar(ActionEvent evento) {
        vlan = (Vlan) evento.getComponent().getAttributes().get("vlanSelecionada");
    }

    public boolean validar() {
        boolean temErro = true;
        if (vlanDao.isNumeroDuplicado(vlan)) {
            message.error("O número da Vlan já está cadastrado na rede " + vlan.getGrupoRede().getNome());
            temErro = false;
        }

        if (vlanDao.isDescricaoDuplicada(vlan)) {
            message.error("A descrição já está sendo usada na rede " + vlan.getGrupoRede().getNome());
            temErro = false;
        }
        return temErro;
    }

}
