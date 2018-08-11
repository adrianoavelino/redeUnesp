package br.com.unesp.dao;

import br.com.unesp.model.Host;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    
    public List<Host> listar() throws Exception {
        Query query = this.em.createQuery("from host h");
        List<Host> hosts = query.getResultList();
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
        hostModificado.setEnderecoIp(host.getEnderecoIp());
        em.merge(hostModificado);
    }     

    public void deletar(Host host) throws Exception {
        this.em.remove(this.em.merge(host));
    }    
}
