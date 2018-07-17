package br.com.unesp.controller;

import br.com.unesp.dao.VlanDao;
import br.com.unesp.model.GrupoRede;
import br.com.unesp.model.Vlan;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class VlanController {
    @EJB
    private VlanDao dao;

    public VlanController() {
    }

    public VlanDao getDao() {
        return dao;
    }

    public void setDao(VlanDao dao) {
        this.dao = dao;
    }
    
    public void salvar() {
        GrupoRede grupoRede = new GrupoRede();
        grupoRede.setId(1);
        Vlan vlan = new Vlan(2,"telefonia", grupoRede);
        this.dao.salvar(vlan);
        System.out.println("Salvando vlan ...");
    }
    
    
    
}
