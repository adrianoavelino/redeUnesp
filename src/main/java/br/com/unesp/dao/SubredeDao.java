package br.com.unesp.dao;

import br.com.unesp.model.Subrede;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public List<Subrede> listar() throws Exception {
        Query query = this.em.createQuery("from subrede s");
        List<Subrede> vlans = query.getResultList();
        return vlans;
    }

    public void atualizar(Subrede subrede) throws Exception {
        Subrede subredeModificada = em.find(Subrede.class, subrede.getId());
        subredeModificada.setNetmask(subrede.getNetmask());
        subredeModificada.setListaEnderecoIpSubrede(subrede.getListaEnderecoIpSubrede());
        em.merge(subredeModificada);
    }

    public void deletar(Subrede subrede) throws Exception {
        this.em.remove(this.em.merge(subrede));
    }

    public List<Subrede> buscarSubredesPorRede(Integer idDaRede) {
        try {
            Query query = this.em.createQuery("select s from subrede s where  rede = :rede");
            query.setParameter("rede", idDaRede);
            List<Subrede> subredes = query.getResultList();
            return subredes;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

}
