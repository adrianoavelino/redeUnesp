package br.com.unesp.dao;

import br.com.unesp.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

    public void salvar(Usuario usuario) {
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

    public boolean isNomeDuplicado(Usuario usuario) {
        String consulta = "select u from usuario u where nome = :nome";
        TypedQuery<Usuario> query = this.em.createQuery(consulta, Usuario.class);
        query.setParameter("nome", usuario.getNome());

        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty() && usuarios.get(0).isDiferente(usuario)) {
            return true;
        }
        return false;
    }

    public boolean isMatriculaDuplicada(Usuario usuario) {
        String consulta = "select u from usuario u where matricula = :matricula";
        TypedQuery<Usuario> query = this.em.createQuery(consulta, Usuario.class);
        query.setParameter("matricula", usuario.getMatricula());
        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty() && usuarios.get(0).isDiferente(usuario)) {
            return true;
        }
        return false;
    }

    public boolean isUsuarioComHost(Integer idDoUsuario) {
        String consultaUsuarioComHost = "select h.usuario.id from host h where h.usuario.id = :idDoUsuario ";
        Query query = this.em.createQuery(consultaUsuarioComHost);
        query.setParameter("idDoUsuario", idDoUsuario);
        List<Integer> usuariosComHost = query.getResultList();
        if (usuariosComHost.isEmpty()) {
            return false;
        }
        return true;
    }
}
