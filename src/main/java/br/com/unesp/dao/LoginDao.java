package br.com.unesp.dao;

import br.com.unesp.model.Login;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LoginDao {
    
    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;
    
    

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void create(Login login){
        em.persist(login);
    }
    
}
