package br.com.unesp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.NotBlank;


@Entity(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Preencha o campo nome")
    private String nome;
    
    @NotBlank(message = "Preencha o campo Matrícula")
    private String matricula;

    @OneToMany(mappedBy = "usuario")
    private List<Host> listaDeHosts = new ArrayList<>();
    
    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Host> getListaDeHosts() {
        return listaDeHosts;
    }

    public void setListaDeHosts(List<Host> listaDeHosts) {
        this.listaDeHosts = listaDeHosts;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + ", matricula=" + matricula + '}';
    }

    

}
    