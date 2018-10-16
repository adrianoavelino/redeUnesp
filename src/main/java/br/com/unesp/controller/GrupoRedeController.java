package br.com.unesp.controller;

import br.com.unesp.dao.GrupoRedeDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.GrupoRede;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "grupoRedeController")
@RequestScoped
public class GrupoRedeController {

    @Inject
    private GrupoRedeDao grupoRedeDao;
    private GrupoRede grupoRede = new GrupoRede();
    private List<GrupoRede> gruposDeRedes;

    @Inject
    private FacesMessages message;

    public GrupoRedeController() {
    }

    public GrupoRede getGrupoRede() {
        return grupoRede;
    }

    public void setGrupoRede(GrupoRede grupoRede) {
        this.grupoRede = grupoRede;
    }

    public GrupoRedeDao getGrupoRedeDao() {
        return grupoRedeDao;
    }

    public void setGrupoRedeDao(GrupoRedeDao grupoRedeDao) {
        this.grupoRedeDao = grupoRedeDao;
    }

    public List<GrupoRede> getGruposDeRedes() {
        return gruposDeRedes;
    }

    public void setGruposDeRedes(List<GrupoRede> gruposDeRedes) {
        this.gruposDeRedes = gruposDeRedes;
    }

    public void salvar() {
        if (grupoRede.getId() == null) {
            this.cadastrar();
        } else {
            this.alterar();
        }
    }

    private void cadastrar() {
        if (grupoRedeDao.isNomeDuplicado(grupoRede)) {
            message.error("O Grupo de Rede  " + grupoRede.getNome() + " já está cadastrado");
        } else {
            grupoRedeDao.salvar(this.grupoRede);
            System.out.println("Salvando grupo de rede ..." + this.grupoRede);
            grupoRede = new GrupoRede();
            message.info("Salvo com sucesso!");
        }
    }

    private void alterar() {
        try {
            if (grupoRedeDao.isNomeDuplicado(grupoRede)) {
                message.error("O Grupo de Rede  " + grupoRede.getNome() + " já está cadastrado");
            } else {
                grupoRedeDao.atualizar(grupoRede);
                grupoRede = new GrupoRede();
                System.out.println("Atualiza grupo");
            }
        } catch (Exception ex) {
            System.out.println("Erro ao atualizar grupo de rede");
        }
    }

    public void deletar(ActionEvent evento) {
        grupoRede = (GrupoRede) evento.getComponent().getAttributes().get("grupoRedeSelecionado");
        try {
            if (grupoRedeDao.isGrupoRedeEmUso(grupoRede.getId())) {
                message.error("Grupo de rede em uso. Remova o grupo de rede \"" + grupoRede.getNome() + "\" de todas as vlans.");
                grupoRede = new GrupoRede();
                return;
            }
            grupoRedeDao.deletar(grupoRede);
            grupoRede = new GrupoRede();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editar(ActionEvent evento) {
        grupoRede = (GrupoRede) evento.getComponent().getAttributes().get("grupoRedeSelecionado");
    }

    public void listar() {
        try {
            gruposDeRedes = grupoRedeDao.listar();
            for (GrupoRede grupoRede1 : gruposDeRedes) {
                System.out.println(grupoRede1.getNome());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar grupo de redes");
        }
    }

}
