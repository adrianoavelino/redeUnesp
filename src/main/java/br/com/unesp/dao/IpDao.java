package br.com.unesp.dao;

import br.com.unesp.model.Ip;
import br.com.unesp.model.Rede;
import br.com.unesp.model.Subrede;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        String consulta = "select "
                + "ip.enderecoIp "
                + "from ip inner join rede on rede.id_rede = ip.rede_id "
                + "where rede.id_rede = :rede and ip.enderecoIp "
                + "not in (select enderecoIp from subrede_ip)";
        Query query = this.em.createNativeQuery(consulta);
        query.setParameter("rede", idDaRede);
        List<Ip> lista = query.getResultList();
        return lista;
    }

    public List<Ip> buscarIpsPorRede(Integer id) {
        Query query = this.em.createQuery("from ip i where rede_id = :rede");
        query.setParameter("rede", id);
        List<Ip> ips = query.getResultList();
        return ips;
    }

//   select  * from  subrede s inner join subrede_ip si  on s.id_subrede = si.id_subrede and id_vlan = 2
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
