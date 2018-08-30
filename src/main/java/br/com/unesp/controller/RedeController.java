package br.com.unesp.controller;

import br.com.unesp.dao.IpDao;
import br.com.unesp.dao.RedeDao;
import br.com.unesp.dao.SubredeDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Ip;
import br.com.unesp.model.Rede;
import java.util.List;
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
    @Inject
    private SubredeDao subredeDao;
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
            if (this.validar()) {
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
            }
        } else {
            if (this.validar()) {
                try {
                    dao.atualizar(rede);
                    rede = new Rede();
                    message.info("Alterado com sucesso!");
                } catch (Exception ex) {
                    message.error("Erro ao alterar rede");
                }
            }
        }
    }

    public void deletar(ActionEvent evento) {
        rede = (Rede) evento.getComponent().getAttributes().get("redeSelecionada");
        if (subredeDao.buscarSubredesPorRede(rede).size() > 0) {
            message.error("Para deletar essa rede você deve deletar todas as subredes da rede " + this.rede.getEndereco());
            rede = new Rede();
            return;
        }
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
            message.info("Rede excluída com sucesso!");
        } catch (Exception ex) {
            message.error("Erro ao deletar rede");
        }
        rede = new Rede();
    }

    public void editar(ActionEvent evento) {
        rede = (Rede) evento.getComponent().getAttributes().get("redeSelecionada");
        System.out.println("Editar");
    }

    public boolean validar() {
        boolean temErro = true;
        if (dao.isEnderecoDuplicado(rede)) {
            message.error("A rede já está cadastrado");
            temErro = false;
        }
        return temErro;
    }

}
