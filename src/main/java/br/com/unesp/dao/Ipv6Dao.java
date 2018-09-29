package br.com.unesp.dao;

import br.com.unesp.model.Ipv6;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class Ipv6Dao {
    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;
    
    public List<Ipv6> listar() {
        Query query = this.em.createQuery("from ipv6 i");
        return query.getResultList();
    }
    
    public void salvar(Ipv6 ipv6) throws Exception{
        this.em.persist(ipv6);
    }
    

    public void atualizar(Ipv6 ipv6) throws Exception {
        Ipv6 ipv6Modificado = em.find(Ipv6.class, ipv6.getId());
        ipv6Modificado.setEndereco(ipv6.getEndereco());
        ipv6.setRede(ipv6.getRede());
        ipv6.setVlan(ipv6.getVlan());
        em.merge(ipv6);
    }    

    public void deletar(Ipv6 ipv6) throws Exception {
        em.remove(em.merge(ipv6));
    }    
    
    public boolean isIpv6Duplicado(Ipv6 ipv6) {
        String consulta = "select i from ipv6 i where endereco = :endereco and vlan = :vlan and rede = :rede";
        TypedQuery<Ipv6> query = this.em.createQuery(consulta, Ipv6.class);
        query.setParameter("endereco", ipv6.getEndereco());
        query.setParameter("vlan", ipv6.getVlan());
        query.setParameter("rede", ipv6.getRede());
        List<Ipv6> ips = query.getResultList();
        if (!ips.isEmpty() && ips.get(0).isDiferente(ipv6)) {
            return true;
        }
        return  false;
    }
}
