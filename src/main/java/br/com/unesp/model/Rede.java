package br.com.unesp.model;

import br.com.unesp.comparator.IpComparator;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "rede")
public class Rede implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rede")
    private Integer id;
    @NotBlank(message = "O campo endereço está vazio")
    private String endereco;
    @Column(name = "enderecoIp", nullable = false)
    @ElementCollection
    @CollectionTable(name = "ip", joinColumns = @JoinColumn(name = "id_rede"))
    private Set<String> listaIps = new HashSet<>();
    
    
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

    public Set<String> getListaIps() {
        Set<String> listaDeEnderecoIpDaSubrede = new TreeSet<String>(new IpComparator());
        listaDeEnderecoIpDaSubrede.addAll(listaIps);
        return listaDeEnderecoIpDaSubrede;

//        return listaIps;
    }

    public void setListaIps(Set<String> listaIps) {
//        Set<String> listaDeEnderecoIpDaSubrede = new TreeSet<String>(new IpComparator());
//        listaDeEnderecoIpDaSubrede.addAll(listaIps);        
        this.listaIps = listaIps;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Rede other = (Rede) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rede{" + "id=" + id + ", endereco=" + endereco + ", listaIps=" + listaIps + '}';
    }

}
