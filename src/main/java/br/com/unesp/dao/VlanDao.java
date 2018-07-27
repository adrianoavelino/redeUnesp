package br.com.unesp.dao;

import br.com.unesp.model.Vlan;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class VlanDao {
    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void salvar(Vlan vlan) {
        this.em.persist(vlan);
    }

    public List<Vlan> listar() throws Exception {
        Query query = this.em.createQuery("from vlan v");
        List<Vlan> vlans = query.getResultList();
        return vlans;
    }

    public void atualizar(Vlan vlan) throws Exception {
        Vlan vlanModificada = em.find(Vlan.class, vlan.getId());
        vlanModificada.setDescricao(vlan.getDescricao());
        vlanModificada.setNumero(vlan.getNumero());
        vlanModificada.setGrupoRede(vlan.getGrupoRede());
        em.merge(vlan);
    }

    public void deletar(Vlan vlan) throws Exception {
        this.em.remove(this.em.merge(vlan));
    }
    
}
