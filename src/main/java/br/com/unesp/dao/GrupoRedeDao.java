package br.com.unesp.dao;

import br.com.unesp.model.GrupoRede;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class GrupoRedeDao {

    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void salvar(GrupoRede grupoRede) {
        this.em.persist(grupoRede);
    }

    public void deletar(GrupoRede grupoRede) throws Exception {
        em.remove(em.merge(grupoRede));
    }

    public void atualizar(GrupoRede grupoRede) throws Exception {
        GrupoRede grupoRedeModificado = em.find(GrupoRede.class, grupoRede.getId());
        grupoRedeModificado.setNome(grupoRede.getNome());
        em.merge(grupoRede);
    }

    public List<GrupoRede> listar() {
        Query query = this.em.createQuery("from grupoRede");
        List<GrupoRede> listaGrupoRedes = query.getResultList();
        return listaGrupoRedes;
    }

    public boolean isNomeDuplicado(GrupoRede gr) {
        String consulta = "select g from grupoRede g where nome = :nome";
        TypedQuery<GrupoRede> query = this.em.createQuery(consulta, GrupoRede.class);
        query.setParameter("nome", gr.getNome());

        List<GrupoRede> g = query.getResultList();
        if (!g.isEmpty() && g.get(0).isDiferente(gr)) {
            return true;
        }
        return false;
    }
    
    public boolean isGrupoRedeEmUso(Integer idDoGrupoDeRede) {
        String consulta = "select v from vlan v join fetch v.grupoRede g where g.id = :idDoGrupoDeRede ";
        Query query = this.em.createQuery(consulta);
        query.setParameter("idDoGrupoDeRede", idDoGrupoDeRede);
        List<GrupoRede> grupoDeRedes = query.getResultList();
        if (grupoDeRedes.isEmpty()) {
            return false;
        }
        return true;
    }

}
