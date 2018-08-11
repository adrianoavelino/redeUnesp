package br.com.unesp.dao;

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

    public List<String> buscarIpsSemVlan(Integer idDaRede) throws Exception {
        String consulta = "select "
                + "ip.enderecoIp "
                + "from ip inner join rede on rede.id_rede = ip.id_rede "
                + "where rede.id_rede = :rede and ip.enderecoIp "
                + "not in (select enderecoIp from subrede_ip)";
        Query query = this.em.createNativeQuery(consulta);
        query.setParameter("rede", idDaRede);
        List<String> lista = query.getResultList();
        return lista;
    }

//   select  * from  subrede s inner join subrede_ip si  on s.id_subrede = si.id_subrede and id_vlan = 2
    public List<String> buscarIpsDaVlan(Integer idDaVlan) {
        String consulta = "select "
                + "si.enderecoIp "
                + "from subrede s inner join subrede_ip si on s.id_subrede = si.id_subrede "
                + "and id_vlan = :idDaVlan";
        Query query = this.em.createNativeQuery(consulta);
        query.setParameter("idDaVlan", idDaVlan);
        List<String> lista;
        try {
            lista = query.getResultList();
        } catch (Exception e) {
            lista = Collections.emptyList();
        }
        return lista;
    }
}
