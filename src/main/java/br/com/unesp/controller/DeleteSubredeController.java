package br.com.unesp.controller;

import br.com.unesp.dao.HostDao;
import br.com.unesp.dao.SubredeDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Rede;
import br.com.unesp.model.Subrede;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "deleteSubredeController")
@RequestScoped
public class DeleteSubredeController {

    @Inject
    private SubredeDao dao;
    @Inject
    private HostDao hostDao;
    private Rede rede;
    @Inject
    private FacesMessages message;

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public void resetar() {
        if (rede != null) {
            if (this.validar()) {
                try {
                    List<Subrede> subredes = dao.buscarSubredesPorRede(rede);
                    for (Subrede sub : subredes) {
                        dao.deletar(sub);
                    }
                    message.info("Subredes deletadas com sucesso!");
                } catch (Exception ex) {
                    message.error("Erro ao deletar Subredes");
                    ex.printStackTrace();
                }
            }
        } else {
            message.error("Selecione uma Rede");
        }
    }

    public boolean validar() {
        try {
            if (hostDao.listarHostComIp(this.rede.getId()).size() > 0) {
                message.error("Para resetar essa rede você deve remover todos os ips dos host da rede " + this.rede.getEndereco());
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Erro ao listar ips da subrede" + ex);
        }
        return true;

    }
}
