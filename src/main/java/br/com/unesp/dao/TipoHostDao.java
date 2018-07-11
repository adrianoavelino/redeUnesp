package br.com.unesp.dao;

import br.com.unesp.model.TipoHost;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TipoHostDao {
    
    @PersistenceContext(unitName = "redeUnespPU")
    EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
        
    public void salvar(TipoHost tipoHost) {
        em.persist(tipoHost);
    }
    
}
