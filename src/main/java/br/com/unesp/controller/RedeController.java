package br.com.unesp.controller;

import br.com.unesp.dao.IpDao;
import br.com.unesp.dao.RedeDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Ip;
import br.com.unesp.model.Rede;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "redeController")
@RequestScoped
public class RedeController {

    @Inject
    private RedeDao dao;
    @Inject
    private IpDao ipDao;
    private Rede rede = new Rede();
    @Inject
    private FacesMessages message;
    private List<String> listaIp;

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

    public List<String> getListaIp() {
        return listaIp;
    }

    public void setListaIp(List<String> listaIp) {
        this.listaIp = listaIp;
    }

    public void salvar() {

        if (rede.getId() == null) {
            List<Ip> ips = Ip.criarListaEnderecoIp(rede);
            try {
                dao.salvar(rede);
                this.rede = new Rede();
                message.info("Rede salva com sucesso!");
            } catch (Exception ex) {
                message.error("Erro ao salvar Rede");
            }
            for (Ip ip : ips) {
                try {
                    ipDao.salvar(ip);
                } catch (Exception ex) {
                    message.error("Erro ao salvar lista de ip");
                }
            }
        } else {
            try {
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
        List<Ip> ips = ipDao.buscarIpsPorRede(rede.getId());

        for (Ip ip : ips) {
            try {
                ipDao.deletar(ip);
            } catch (Exception ex) {
                message.error("Erro ao deletar ip:" + ex.toString());
            }
        }

        try {
            dao.deletar(rede);
            message.info("Rede excluida com sucesso!");
        } catch (Exception ex) {
            message.error("Erro ao deletar rede");
        }
    }

    public void editar(ActionEvent evento) {
        rede = (Rede) evento.getComponent().getAttributes().get("redeSelecionada");
        System.out.println("Editar");
    }

}
