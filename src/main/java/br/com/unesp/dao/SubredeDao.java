package br.com.unesp.dao;

import br.com.unesp.model.Rede;
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
        String sql = "select "
                        + "distinct s "
                    + "from "
                        + "subrede s "
                    + "join fetch s.ips si "
                    + "join fetch si.rede r "
                    + "join fetch s.vlan v "
                    + "join fetch v.grupoRede g "
                    + "left join fetch si.host h "
                    + "left join fetch h.usuario u "
                    + "left join fetch h.tipo t order by v.numero ";
        Query query = this.em.createQuery(sql);
        List<Subrede> vlans = query.getResultList();
        return vlans;
    }

    public void atualizar(Subrede subrede) throws Exception {
        Subrede subredeModificada = em.find(Subrede.class, subrede.getId());
        subredeModificada.setNetmask(subrede.getNetmask());
        em.merge(subredeModificada);
    }

    public void deletar(Subrede subrede) throws Exception {
        this.em.remove(this.em.merge(subrede));
    }

    public List<Subrede> buscarSubredesPorRede(Rede rede) {
        try {
            Query query = this.em.createQuery("select s from subrede s where  s.rede = :rede");
            query.setParameter("rede", rede);
            List<Subrede> subredes = query.getResultList();
            return subredes;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }
    
}
