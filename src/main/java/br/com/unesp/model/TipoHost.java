package br.com.unesp.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "tipo_host")
public class TipoHost implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_host")
    private Integer id;
    @NotBlank(message = "Campo Descrição do Tipo em branco")
    @Column(unique = true)
    private String tipo;

    public TipoHost() {
    }

    public TipoHost(String tipo) {
        this.tipo = tipo;
    }

    public TipoHost(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public boolean isDiferente(TipoHost tipo) {
        return this.id != tipo.getId();
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final TipoHost other = (TipoHost) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "TipoHost{" + "id=" + id + ", tipo=" + tipo + '}';
    }
    
}
