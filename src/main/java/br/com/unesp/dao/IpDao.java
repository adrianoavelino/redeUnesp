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

    public List<Object[]> listar(Integer idDaRede) throws Exception {
        String sql = "select "
                    + "i.enderecoIp, "
                    + "h.nome, "
                    + "h.macAddres, "
                    + "u.nome as nomeUsuario, "
                    + "u.matricula, "
                    + "v.numero, "
                    + "v.descricao  "
                + "from "
                    + "ip i "
                + "left outer join host h "
                    + "on  h.ip_ip_id = i.ip_id "
                + "left outer join usuario u "
                    + "on h.id_usuario = u.id  "
                + "left outer join subrede_ip si "
                    + "on si.enderecoIp = i.ip_id "
                + "left outer join subrede s "
                    + "on s.id_subrede = si.id_subrede "
                + "left outer join vlan v "
                    + "on v.id_vlan = s.vlan_id "
                + "where "
                    + "i.rede_id = :rede "
                + "order by i.ip_id";
        Query query = this.em.createNativeQuery(sql);
        query.setParameter("rede", idDaRede);
        List<Object[]> ips = query.getResultList();
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
        List<String> ipsSemSubrede = this.buscarIpsSubrede();
        String consulta;
        List<Ip> rede;
        if (ipsSemSubrede.isEmpty()) {
            consulta = "select i from ip i inner join i.rede r where r.id = :rede";
            TypedQuery<Ip> query = em.createQuery(consulta, Ip.class);
            query.setParameter("rede", idDaRede);
            rede = query.getResultList();
        } else {
            consulta = "select i from ip i inner join i.rede r where i.enderecoIp not in :ipsSemSubrede and r.id = :rede";
            TypedQuery<Ip> query = em.createQuery(consulta, Ip.class);
            query.setParameter("ipsSemSubrede", ipsSemSubrede);
            query.setParameter("rede", idDaRede);
            rede = query.getResultList();
        }
        try {
            return rede;
        } catch (Exception e) {
            System.out.println("Erro ao selecionar ips sem vlan" + e);
            return Collections.emptyList();
        }
    }

    public List<Ip> buscarIpsSemVlanOld(Integer idDaRede) throws Exception {
        String consulta = "select distinct i from ip i inner join i.rede r "
                + "where i.enderecoIp not in :ipsSemSubrede "
                + " and r.id = :rede ";
        try {
            List<String> ipsSemSubrede = this.buscarIpsSubrede();
            TypedQuery<Ip> query = em.createQuery(consulta, Ip.class);
            query.setParameter("ipsSemSubrede", ipsSemSubrede);
            query.setParameter("rede", idDaRede);
            List<Ip> rede = query.getResultList();
            return rede;
        } catch (Exception e) {
            System.out.println("Erro ao selecionar ips sem vlan" + e);
            return Collections.emptyList();
        }
    }

    public List<String> buscarIpsSubrede() throws Exception {
        String consulta = "select ip.enderecoIp from subrede_ip "
                + " inner join ip on subrede_ip.enderecoIp = ip.ip_id";
        try {
            Query query = this.em.createNativeQuery(consulta);
            List<String> ipsDaSubrede = query.getResultList();
            return ipsDaSubrede;

        } catch (Exception e) {
            return Collections.emptyList();
        }
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

    public List<Ip> buscarIpsPorVlan(Integer vlan) {
        String consulta = "select  i from subrede s inner join s.ips i where s.vlan.id = :vlan";
        Query query = this.em.createQuery(consulta);
        query.setParameter("vlan", vlan);
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

//    buscar vlan por ip
//    select * from subrede s inner join subrede_ip si on s.id_subrede = si.id_subrede where si.enderecoIp = 1;
}
