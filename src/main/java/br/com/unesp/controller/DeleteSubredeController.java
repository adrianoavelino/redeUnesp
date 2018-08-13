package br.com.unesp.controller;

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
    private Rede rede;
    @Inject
    private FacesMessages mesage;

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public void resetar() {
        if (rede != null) {
            try {
                List<Subrede> subredes = dao.buscarSubredesPorRede(rede);
                for (Subrede sub : subredes) {
                    dao.deletar(sub);
                }
                mesage.info("Subredes deletadas com sucesso!");
            } catch (Exception ex) {
                mesage.error("Erro ao deletar Subredes");
                ex.printStackTrace();
            }
        } else {
            mesage.error("Selecione uma Rede");
        }
    }
}
