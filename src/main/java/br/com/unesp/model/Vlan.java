package br.com.unesp.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "vlan")
public class Vlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vlan")
    private Integer id;
    @NotNull(message = "O campo número está vazio")
    private Integer numero;
    @NotBlank(message = "O campo Descrição está em branco")
    private String descricao;
    @OneToOne
    @JoinColumn(name = "id_grupoRede")
    @NotNull(message = "Selecione um Grupo de Rede")
    private GrupoRede grupoRede;

    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_vlan", referencedColumnName = "id_vlan")
    private Set<Subrede> listaSubrede;

    public Vlan() {
    }

    public Vlan(Integer id, String descricao, GrupoRede grupoRede) {
        this.id = id;
        this.descricao = descricao;
        this.grupoRede = grupoRede;
    }

    public GrupoRede getGrupoRede() {
        return grupoRede;
    }

    public void setGrupoRede(GrupoRede grupoRede) {
        this.grupoRede = grupoRede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Subrede> getListaSubrede() {
        return listaSubrede;
    }

    public void setListaSubrede(Set<Subrede> listaSubrede) {
        this.listaSubrede = listaSubrede;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vlan other = (Vlan) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vlan{" + "id=" + id + ", numero=" + numero + ", descricao=" + descricao + " '}'";
    }

}
