package br.com.unesp.dao;

import br.com.unesp.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UsuarioDao {
    
    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void salvar(Usuario usuario){
        em.persist(usuario);
    }

    public List<Usuario> listar() throws Exception {
        Query query = this.em.createQuery("from usuario u");
        List<Usuario> usuarios = query.getResultList();
        return usuarios;
    }

    public void atualizar(Usuario usuario) throws Exception {
        Usuario usuarioModificado = em.find(Usuario.class, usuario.getId());
        usuarioModificado.setNome(usuario.getNome());
        usuarioModificado.setMatricula(usuario.getMatricula());
        em.merge(usuarioModificado);
    }

    public void deletar(Usuario usuario) throws Exception {
        this.em.remove(this.em.merge(usuario));
    }

    public Usuario buscarPorId(Integer idDoUsuario) {
        return this.em.find(Usuario.class, idDoUsuario);
    }    
    
}
