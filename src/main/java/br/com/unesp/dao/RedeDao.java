package br.com.unesp.dao;

import br.com.unesp.model.Rede;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class RedeDao {

    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void salvar(Rede rede) throws Exception {
        em.persist(rede);
    }

    public List<Rede> listar() throws Exception {
        Query query = this.em.createQuery("from rede r");
        List<Rede> redes = query.getResultList();
        return redes;
    }

    public void atualizar(Rede rede) throws Exception {
        Rede redeModificada = em.find(Rede.class, rede.getId());
        redeModificada.setEndereco(rede.getEndereco());
        em.merge(rede);
    }

    public void deletar(Rede rede) throws Exception {
        this.em.remove(this.em.merge(rede));
    }
}
