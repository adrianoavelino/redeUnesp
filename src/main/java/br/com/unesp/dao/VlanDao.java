package br.com.unesp.dao;

import br.com.unesp.model.Vlan;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VlanDao {
    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void salvar(Vlan vlan) {
        this.em.persist(vlan);
    }
    
}
