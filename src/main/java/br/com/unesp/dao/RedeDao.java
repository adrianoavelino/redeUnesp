package br.com.unesp.dao;

import br.com.unesp.model.Rede;
import br.com.unesp.model.TipoEndereco;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

    public List<Rede> listarRedesIpv6() throws Exception {
        Query query = this.em.createQuery("from rede r where r.tipoEndereco = :tipo");
        query.setParameter("tipo", TipoEndereco.IPV6);
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

    public boolean isEnderecoDuplicado(Rede rede) {
        String consulta = "select r from rede r where endereco = :endereco";
        TypedQuery<Rede> query = this.em.createQuery(consulta, Rede.class);
        query.setParameter("endereco", rede.getEndereco());

        List<Rede> redes = query.getResultList();
        if (!redes.isEmpty() && redes.get(0).isDiferente(rede)) {
            return true;
        }
        return false;
    }
}
