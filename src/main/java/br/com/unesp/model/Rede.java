package br.com.unesp.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "rede")
public class Rede implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rede_id")
    private Integer id;
    @NotBlank(message = "O campo endereço está vazio")
    private String endereco;
    @Column(name = "tipo_endereco", nullable = false)
    @NotNull(message = "Selecione o tipo do endereço")
    @Enumerated
    private TipoEndereco tipoEndereco;
    @OneToMany(mappedBy = "rede")
    private List<Ip> ips;

    public Rede() {
    }

    public Rede(String endereco) {
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public TipoEndereco getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(TipoEndereco tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public boolean isDiferente(Rede rede) {
        return this.id != rede.getId();
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
        final Rede other = (Rede) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rede{" + "id=" + id + ", endereco=" + endereco + '}';
    }

}
