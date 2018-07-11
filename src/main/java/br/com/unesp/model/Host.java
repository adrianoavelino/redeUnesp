package br.com.unesp.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "host")
public class Host implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_host")
    private Integer id;
    private String nome;
    private String macAddres;
    @OneToOne(optional = false)
    @JoinColumn(name = "id_tipo_host")
    private TipoHost tipo;

    public Host() {
    }

    public Host(String nome, String macAddres, TipoHost tipo) {
        this.nome = nome;
        this.macAddres = macAddres;
        this.tipo = tipo;
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

    public String getMacAddres() {
        return macAddres;
    }

    public void setMacAddres(String macAddres) {
        this.macAddres = macAddres;
    }

    public TipoHost getTipo() {
        return tipo;
    }

    public void setTipo(TipoHost tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Host other = (Host) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Host{" + "id=" + id + ", nome=" + nome + ", macAddres=" + macAddres + '}';
    }
    
}
