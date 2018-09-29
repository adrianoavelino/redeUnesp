package br.com.unesp.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "ipv6")
public class Ipv6 implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull(message = "Selecione uma Rede IPV6")
    @JoinColumn(name = "rede_id")
    private Rede rede;
    @ManyToOne
    @JoinColumn(name = "vlan_id")
    @NotNull(message = "Selecione uma Vlan")
    private Vlan vlan;
    @NotEmpty(message = "Preecha o campo endereço")
    private String endereco;

    public Ipv6() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    
    
    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan) {
        this.vlan = vlan;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isDiferente(Ipv6 ipv6) {
        return this.id != ipv6.getId();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Ipv6 other = (Ipv6) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ipv6{" + "id=" + id + ", endereco=" + endereco + ", vlan=" + vlan + '}';
    }
    
}
