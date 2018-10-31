package br.com.unesp.dao;

import br.com.unesp.model.Login;
import br.com.unesp.model.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LoginDao {

    @PersistenceContext(unitName = "redeUnespPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Login> listar() throws Exception {
        Query query = this.em.createQuery("from login l");
        List<Login> logins = query.getResultList();
        return logins;
    }

    public void salvar(Login login) {
        em.persist(login);
    }

    public void atualizar(Login login) throws Exception {
        Login loginModificado = em.find(Login.class, login.getId());
        loginModificado.setNome(login.getNome());
        loginModificado.setEmail(login.getEmail());
        if (!login.getSenha().isEmpty()) {
            loginModificado.setSenha(login.getSenha());
        }
        em.merge(loginModificado);
    }

    public void deletar(Login login) throws Exception {
        this.em.remove(this.em.merge(login));
    }
}
