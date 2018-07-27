package br.com.unesp.controller;

import br.com.unesp.dao.RedeDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Ip;
import br.com.unesp.model.Rede;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "redeController")
@RequestScoped
public class RedeController {

    @Inject
    private RedeDao dao;
    private Rede rede = new Rede();
    @Inject
    private FacesMessages message;
    private Set<String> listaIp;

    public RedeDao getDao() {
        return dao;
    }

    public void setDao(RedeDao dao) {
        this.dao = dao;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public RedeController() {
    }

    public Set<String> getListaIp() {
        return listaIp;
    }

    public void setListaIp(Set<String> listaIp) {
        this.listaIp = listaIp;
    }

    public void salvar() {
        if (rede.getId() == null) {
            Set<String> list = Ip.criarListaEnderecoIp(rede);
            rede.setListaIps(list);
            dao.salvar(this.rede);
            System.out.println("Salva Rede" + rede);
            rede = new Rede();
            message.info("Cadastrado com sucesso!");
        } else {
            try {
                Set<String> list = Ip.criarListaEnderecoIp(rede);
                rede.setListaIps(list);
                dao.atualizar(rede);
                rede = new Rede();
                message.info("Alterado com sucesso!");
            } catch (Exception ex) {
                message.error("Erro ao alterar rede");
            }
        }
    }

    public void deletar(ActionEvent evento) {
        rede = (Rede) evento.getComponent().getAttributes().get("redeSelecionada");
        try {
            dao.deletar(rede);
        } catch (Exception ex) {
            message.error("Erro ao deletar rede");
        }
    }

    public void editar(ActionEvent evento) {
        rede = (Rede) evento.getComponent().getAttributes().get("redeSelecionada");
        System.out.println("Editar");
    }
    
}
