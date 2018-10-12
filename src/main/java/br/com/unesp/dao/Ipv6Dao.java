package br.com.unesp.dao;

import br.com.unesp.model.Ipv6;
import java.util.Collections;
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
        Query query = this.em.createQuery("from ipv6 i join fetch i.vlan v join fetch v.grupoRede g join fetch i.rede r");
        return query.getResultList();
    }

    public List<Ipv6> buscarIpv6PorVlan(Integer vlan) {
        String consulta = "select "
                + " i "
                + "from "
                + "ipv6 i join fetch i.vlan v "
                + "join fetch v.grupoRede g "
                + "join fetch i.rede r "
                + "where v.id = :idDaVlan "
                + "and i.id not in :idsDosIpv6sEmUso ";
        Query query = this.em.createQuery(consulta);
        List<String> idsDosIpv6sEmUso = this.buscarIpv6sHosts();
        query.setParameter("idDaVlan", vlan);
        query.setParameter("idsDosIpv6sEmUso", idsDosIpv6sEmUso);
        List<Ipv6> ips = query.getResultList();
        return ips;
    }
    
    public Ipv6 buscarIpv6PorId(Long idDoIpv6) {
        String consulta = "from ipv6 i join fetch i.vlan v join fetch i.rede r join fetch v.grupoRede g where i.id = :idDoIpv6 ";
        Query query = this.em.createQuery(consulta);
        query.setParameter("idDoIpv6", idDoIpv6);
        List<Ipv6> ipv6s = query.getResultList();
        if(!ipv6s.isEmpty()){
            return ipv6s.get(0);
        }
        return null;
    }

    public void salvar(Ipv6 ipv6) throws Exception {
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
        return false;
    }

    public boolean isIpv6EmUso(Ipv6 ipv6) {
        if (this.buscarIpv6sHosts().isEmpty()) {
            return false;
        }
        List<String> hostsComIpv6 = this.buscarIpv6sHosts();
        String consulta = "from ipv6 i join fetch i.vlan v join fetch v.grupoRede g join fetch i.rede r where i.id = :idDoIpv6 and i.id in :listaDeHost ";
        int quantidadeDeHostsComIpv6 = 0;
        try {
            Query query = this.em.createQuery(consulta);
            query.setParameter("idDoIpv6", ipv6.getId());
            query.setParameter("listaDeHost", hostsComIpv6);
            quantidadeDeHostsComIpv6 = query.getResultList().size();
        } catch (Exception e) {
            System.out.println("Erro ao verificar ipv6 em uso nos hosts" + e.toString());
        }
        if (quantidadeDeHostsComIpv6 == 0) {
            return false;
        }
        return true;
    }

    private List<String> buscarIpv6sHosts() {
        String consulta = "select h.ipv6.id from host h where h.ipv6.id != null";
        Query query = this.em.createQuery(consulta);
        List<String> lista;
        try {
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao selecionar ipv6s dos hosts" + e);
            lista = Collections.emptyList();
        }
        return lista;
    }
}
