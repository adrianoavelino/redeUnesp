package br.com.unesp.dao;

import br.com.unesp.model.Host;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HostDao {
    
    @PersistenceContext(unitName = "redeUnespPU")
    EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void salvar(Host host) {
        em.persist(host);
    }
}
