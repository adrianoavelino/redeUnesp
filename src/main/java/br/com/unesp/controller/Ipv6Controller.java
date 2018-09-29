package br.com.unesp.controller;

import br.com.unesp.dao.Ipv6Dao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Ipv6;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "ipv6Controller")
@RequestScoped
public class Ipv6Controller {

    private Ipv6 ipv6 = new Ipv6();
    @Inject
    private Ipv6Dao ipv6Dao;
    @Inject
    private FacesMessages message;

    public Ipv6 getIpv6() {
        return ipv6;
    }

    public void setIpv6(Ipv6 ipv6) {
        this.ipv6 = ipv6;
    }

    public Ipv6Dao getIpv6Dao() {
        return ipv6Dao;
    }

    public void setIpv6Dao(Ipv6Dao ipv6Dao) {
        this.ipv6Dao = ipv6Dao;
    }

    public void salvar() {
        if(!this.validar()) {
            return;
        }
        try {
            if (this.ipv6.getId() == null) {
                ipv6Dao.salvar(this.ipv6);
                message.info("IPV6 salvo com sucesso!");
            } else {
                ipv6Dao.atualizar(ipv6);
                message.info("IPV6 alterado com sucesso!");
            }
            this.ipv6 = new Ipv6();
        } catch (Exception ex) {
            message.error("Erro ao salvar ipv6");
            System.out.println("Erro ao salvar ipv6" + ex.toString());
        }
    }

    public void deletar(ActionEvent evento) {
        ipv6 = (Ipv6) evento.getComponent().getAttributes().get("ipv6Selecionado");
        try {
            ipv6Dao.deletar(ipv6);
            ipv6 = new Ipv6();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editar(ActionEvent evento) {
        this.ipv6 = (Ipv6) evento.getComponent().getAttributes().get("ipv6Selecionado");
    }

    private boolean validar() {
        boolean temErro = true;
        if (ipv6Dao.isIpv6Duplicado(ipv6)) {
            message.error("O IPV6 já está cadastrado");
            temErro = false;
        }
        return temErro;
    }
}
