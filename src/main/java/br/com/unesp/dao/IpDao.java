package br.com.unesp.dao;

import br.com.unesp.model.Ip;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class IpDao {

    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

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

    public List<String> buscarIpsSubrede() throws Exception {
        String consulta = "select ip.enderecoIp from subrede_ip "
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

    public List<Ip> buscarIpsDaVlan(Integer idDaVlan) {
        String consulta = "select "
                + "si.enderecoIp "
                + "from subrede s inner join subrede_ip si on s.id_subrede = si.id_subrede "
                + "and id_vlan = :idDaVlan";
        Query query = this.em.createNativeQuery(consulta);
        query.setParameter("idDaVlan", idDaVlan);
        List<Ip> lista;
        try {
            lista = query.getResultList();
        } catch (Exception e) {
            lista = Collections.emptyList();
        }
        return lista;
    }
}
