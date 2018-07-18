package br.com.unesp.dao;

import br.com.unesp.model.Subrede;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SubredeDao {
    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void save(Subrede subrede) {
        this.em.persist(subrede);
    }
    
}
