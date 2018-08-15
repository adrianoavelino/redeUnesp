package br.com.unesp.dao;

import br.com.unesp.model.Ip;
import br.com.unesp.model.Subrede;
import br.com.unesp.model.Vlan;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class IpDao {

    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;
    
    @Inject
    private SubredeDao subredeDao;

    public List<Ip> listar() throws Exception {
        Query query = this.em.createQuery("from ip i");
        List<Ip> ips = query.getResultList();
        return ips;
    }

    public void salvar(Ip ip) throws Exception {
        em.persist(ip);
    }

    public void atualizar(Ip ip) throws Exception {
        Ip ipModificado = em.find(Ip.class, ip.getId());
        ipModificado.setEnderecoIp(ip.getEnderecoIp());
        ipModificado.setRede(ip.getRede());
        em.merge(ip);
    }

    public void deletar(Ip ip) throws Exception {
        this.em.remove(this.em.merge(ip));
    }

    public List<Ip> buscarIpsSemVlan(Integer idDaRede) throws Exception {
        String consulta = "select distinct i from ip i inner join i.rede r "
                + "where i.enderecoIp not in :ipsSemSubrede "
                + " and r.id = :rede ";
        List<String> ipsSemSubrede = this.buscarIpsSubrede();
        TypedQuery<Ip> query = em.createQuery(consulta, Ip.class);
        query.setParameter("ipsSemSubrede", ipsSemSubrede);
        query.setParameter("rede", idDaRede);
        List<Ip> rede = query.getResultList();
        return rede;
    }

    private List<String> buscarIpsSubrede() throws Exception {
        String consulta = "select distinct ip.enderecoIp from subrede_ip "
                + " inner join ip on subrede_ip.enderecoIp = ip.ip_id";
        Query query = this.em.createNativeQuery(consulta);
        List<String> ipsDaSubrede = query.getResultList();
        return ipsDaSubrede;
    }

    public List<Ip> buscarIpsPorRede(Integer id) {
        Query query = this.em.createQuery("from ip i where rede_id = :rede");
        query.setParameter("rede", id);
        List<Ip> ips = query.getResultList();
        return ips;
    }

    public List<Ip> buscarIpsDaVlan(Integer vlan) {
        String consulta = "select distinct i from subrede s inner join s.ips i inner join s.vlan v where v.id = :vlan and i.enderecoIp not in :ipsHosts";
        Query query = this.em.createQuery(consulta);
        List<String> ipsHosts = this.getIpsEmUso();
        query.setParameter("vlan", vlan);
        query.setParameter("ipsHosts", ipsHosts);
        List<Ip> lista;
        try {
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao selecionar ips da subrede" + e);
            lista = Collections.emptyList();
        }
        return lista;
    }

    private List<String> getIpsEmUso() {
        List<String> ipsEmUso = new ArrayList<>();
        ipsEmUso.addAll(this.buscarIpsHosts());
        ipsEmUso.addAll(this.buscarIpsDeRedeGatewayEBroadcast());
        return ipsEmUso;
    }

    private List<String> buscarIpsHosts() {
        String consulta = "select h.ip.enderecoIp from host h";
        Query query = this.em.createQuery(consulta);
        List<String> lista;
        try {
            lista = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao selecoinar ips dos hosts" + e);
            lista = Collections.emptyList();
        }
        return lista;
    }

    private List<String> buscarIpsDeRedeGatewayEBroadcast() {
        List<String> ipsDeRedeGatewayEBrodcast = new ArrayList<>();
        try {
            List<Subrede> subredes = this.subredeDao.listar();
            for (Subrede subrede : subredes) {
                int quantidadeDeIps = subrede.getIps().size();
                ipsDeRedeGatewayEBrodcast.add(subrede.getIps().get(0).getEnderecoIp());
                ipsDeRedeGatewayEBrodcast.add(subrede.getIps().get(quantidadeDeIps - 2).getEnderecoIp());
                ipsDeRedeGatewayEBrodcast.add(subrede.getIps().get(quantidadeDeIps - 1).getEnderecoIp());
            }
        } catch (Exception e) {
            System.out.println("Erro ao selecionar ips das Subredes" + e);
            ipsDeRedeGatewayEBrodcast = Collections.emptyList();
        }
        return ipsDeRedeGatewayEBrodcast;
    }

    private List<Subrede> buscarSubredes() {
        String consulta = "select s from subrede s";
        Query query = this.em.createQuery(consulta);
        List<Subrede> ipsDaSubrede;
        try {
            ipsDaSubrede = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao selecionar Subrede" + e);
            ipsDaSubrede = Collections.emptyList();
        }
        return ipsDaSubrede;
    }
}
