package br.com.unesp.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class IpDao {
    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;
    
   public List<String> buscarIpsSemVlan(Integer idDaRede) throws Exception{
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
}
