package br.com.unesp.dao;

import br.com.unesp.model.Vlan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
        Query query = this.em.createQuery("from vlan v join fetch v.grupoRede");
        List<Vlan> vlans = query.getResultList();
        return vlans;
    }

    public void atualizar(Vlan vlan) throws Exception {
        Vlan vlanModificada = em.find(Vlan.class, vlan.getId());
        vlanModificada.setDescricao(vlan.getDescricao());
        vlanModificada.setNumero(vlan.getNumero());
        vlanModificada.setGrupoRede(vlan.getGrupoRede());
        em.merge(vlanModificada);
    }

    public void deletar(Vlan vlan) throws Exception {
        this.em.remove(this.em.merge(vlan));
    }

    public Vlan buscarPorId(Integer idRede) {
        return this.em.find(Vlan.class, idRede);
    }

    public Vlan buscarPorIp(Long id) {

        if (id != null) {

            try {
                String consulta = "select v from subrede s join s.ips i join s.vlan v where i.id = :id";
                Query query = em.createQuery(consulta);
                query.setParameter("id", id);
                Vlan vlanProcurada = (Vlan) query.getResultList().get(0);
                return vlanProcurada;
            } catch (Exception e) {
                System.out.println("Erro ao selecionar vlan por Ip" + e);
                return new Vlan();
            }
        }
        return new Vlan();
    }

    public List<Vlan> buscarVlanPorRede(Integer idDaRede) {
        Query query = this.em.createQuery("select v from subrede s inner join s.vlan v where s.rede.id = :rede order by v.numero");
        query.setParameter("rede", idDaRede);
        List<Vlan> vlans = query.getResultList();
        return vlans;
    }

    public boolean isNumeroDuplicado(Vlan vlan) {
        String consulta = "select v from vlan v where v.numero = :numero and v.grupoRede.id = :grupoRede ";
        TypedQuery<Vlan> query = this.em.createQuery(consulta, Vlan.class);
        query.setParameter("numero", vlan.getNumero());
        query.setParameter("grupoRede", vlan.getGrupoRede().getId());
        List<Vlan> vlans = query.getResultList();
        if (!vlans.isEmpty() && vlans.get(0).isDiferente(vlan)) {
            return true;
        }
        return false;
    }

    public boolean isDescricaoDuplicada(Vlan vlan) {
        String consulta = "select v from vlan v where v.descricao = :descricao and v.grupoRede.id = :grupoRede ";
        TypedQuery<Vlan> query = this.em.createQuery(consulta, Vlan.class);
        query.setParameter("descricao", vlan.getDescricao());
        query.setParameter("grupoRede", vlan.getGrupoRede().getId());
        List<Vlan> vlans = query.getResultList();
        if (!vlans.isEmpty() && vlans.get(0).isDiferente(vlan)) {
            return true;
        }
        return false;
    }

    public boolean isVlanEmUso(Integer idDaVlan) {
        String consultaVlansEmUsoPelasSubredes = "select s.vlan.id from subrede s where s.vlan.id = :idDavlan ";
        Query query = this.em.createQuery(consultaVlansEmUsoPelasSubredes);
        query.setParameter("idDavlan", idDaVlan);
        List<Integer> vlansEmUsoPelasSubredes = query.getResultList();
        
        String consultaVlansEmUsoPelosIpv6s = "select i.vlan.id from ipv6 i where i.vlan.id = :idDavlan ";
        Query queryIpv6 = this.em.createQuery(consultaVlansEmUsoPelosIpv6s);
        queryIpv6.setParameter("idDavlan", idDaVlan);
        List<Integer> vlansEmUsoPelosIpv6s = queryIpv6.getResultList();
        
        List<Integer> vlans = new ArrayList<>();
        vlans.addAll(vlansEmUsoPelasSubredes);
        vlans.addAll(vlansEmUsoPelosIpv6s);
        if (vlans.isEmpty()) {
            return false;
        }
        return true;
    }

}
