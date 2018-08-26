package br.com.unesp.dao;

import br.com.unesp.model.Host;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class HostDao {

    @PersistenceContext(unitName = "redeUnespPU")
    EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Object[]> listar() throws Exception {

        String sql = "select "
                + "host.id_host, "
                + "host.nome, "
                + "host.macAddres, "
                + "tipo_host.tipo, "
                + "usuario.nome as nomeUsuario, "
                + "vlan.descricao, "
                + "ip.enderecoIp, "
                + "tipo_host.id_tipo_host, "
                + "usuario.id, "
                + "ip.ip_id "
                + "from "
                    + "host "
                + "inner join "
                    + "tipo_host "
                        + "on host.id_tipo_host = tipo_host.id_tipo_host "
                + "left join "
                    + "usuario "
                        + "on host.id_usuario = usuario.id "
                + "left join "
                    + "ip "
                        + "on host.ip_ip_id = ip.ip_id "
                + "left join "
                    + "subrede_ip "
                        + "on ip.ip_id = subrede_ip.enderecoIp "
                + "left join "
                    + "subrede "
                        + "on subrede.id_subrede = subrede_ip.id_subrede "
                + "left join "
                    + "vlan "
                        + "on subrede.vlan_id = vlan.id_vlan "
                + "order by "
                    + "host.id_host";
        Query query = this.em.createNativeQuery(sql);
        List<Object[]> hosts = query.getResultList();
        return hosts;
    }

    public void salvar(Host host) {
        this.em.persist(host);
    }

    public void atualizar(Host host) throws Exception {
        Host hostModificado = em.find(Host.class, host.getId());
        hostModificado.setNome(host.getNome());
        hostModificado.setUsuario(host.getUsuario());
        hostModificado.setMacAddres(host.getMacAddres());
        hostModificado.setTipo(host.getTipo());
        hostModificado.setIp(host.getIp());
        em.merge(hostModificado);
    }

    public void deletar(Host host) throws Exception {
        this.em.remove(this.em.merge(host));
    }
    
    public boolean isNomeDuplicado(Host host) {
        String consulta = "select h from host h where h.nome = :nome";
        TypedQuery<Host> query = this.em.createQuery(consulta, Host.class);
        query.setParameter("nome", host.getNome());

        List<Host> hosts = query.getResultList();
        if (!hosts.isEmpty() && hosts.get(0).isDiferente(host)) {
            return true;
        }
        return false;
    }    
    
    public boolean isMacAddressDuplicado(Host host) {
        String consulta = "select h from host h where h.macAddres = :macAddres";
        TypedQuery<Host> query = this.em.createQuery(consulta, Host.class);
        query.setParameter("macAddres", host.getMacAddres());

        List<Host> hosts = query.getResultList();
        if (!hosts.isEmpty() && hosts.get(0).isDiferente(host)) {
            return true;
        }
        return false;
    }    
}
