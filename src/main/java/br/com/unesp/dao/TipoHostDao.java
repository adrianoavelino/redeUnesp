package br.com.unesp.dao;

import br.com.unesp.model.TipoHost;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class TipoHostDao {
    
    @PersistenceContext(unitName = "redeUnespPU")
    EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<TipoHost> listar() throws Exception{
        Query query = this.em.createQuery("from tipo_host t");
        List<TipoHost> tiposHosts = query.getResultList();
        return tiposHosts;
    }

    public void salvar(TipoHost tipoHost) {
        this.em.persist(tipoHost);
    }
    

    public void deletar(TipoHost tipoHost) throws Exception {
        this.em.remove(this.em.merge(tipoHost));
    }


    public void atualizar(TipoHost tipoHost) throws Exception {
        TipoHost tipoHostModificado = em.find(TipoHost.class, tipoHost.getId());
        tipoHostModificado.setTipo(tipoHost.getTipo());
        em.merge(tipoHost);
    }    
    
    public boolean isTipoHostDuplicado(TipoHost tipoHost) {
        String consulta = "select t from tipo_host t where tipo = :tipo";
        TypedQuery<TipoHost> query = this.em.createQuery(consulta, TipoHost.class);
        query.setParameter("tipo", tipoHost.getTipo());

        List<TipoHost> tipo = query.getResultList();
        if (!tipo.isEmpty() && tipo.get(0).isDiferente(tipoHost)) {
            return true;
        }
        return false;
    }    
    
    public boolean isTipoHostDuplicado(TipoHost tipoHost, int id) {
        String consulta = "select t from tipo_host t where tipo = :tipo";
        TypedQuery<TipoHost> query = this.em.createQuery(consulta, TipoHost.class);
        query.setParameter("tipo", tipoHost.getTipo());

        List<TipoHost> tipo = query.getResultList();
        int i = tipo.get(0).getId();
        
        if (!tipo.isEmpty() && i != id) {
            return true;
        }
        return false;
    }

    public boolean isTipoHostEmUso(Integer idDoTipoHost) {
        String consultaTipoHostEmUso = "select h.tipo.id from host h where h.tipo.id = :idDoTipoHost ";
        Query query = this.em.createQuery(consultaTipoHostEmUso);
        query.setParameter("idDoTipoHost", idDoTipoHost);
        List<Integer> tiposDeHostsEmUso = query.getResultList();
        if (tiposDeHostsEmUso.isEmpty()) {
            return false;
        }
        return true;
    }
}
