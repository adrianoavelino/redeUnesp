package br.com.unesp.controller;

import br.com.unesp.dao.GrupoRedeDao;
import br.com.unesp.model.GrupoRede;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class GrupoRedeController {
    @EJB
    private GrupoRedeDao dao;

    public GrupoRedeDao getDao() {
        return dao;
    }

    public void setDao(GrupoRedeDao dao) {
        this.dao = dao;
    }

    public void salvar() {
        GrupoRede grupoRede = new GrupoRede("Rede Unesp");
        dao.salvar(grupoRede);
        System.out.println("Salvando grupo de rede ...");
    }
}
