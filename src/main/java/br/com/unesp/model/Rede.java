package br.com.unesp.model;

import br.com.unesp.comparator.IpComparator;
import com.sun.xml.internal.ws.developer.StreamingAttachment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
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
import javax.persistence.MapKeyColumn;
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ip", joinColumns = @JoinColumn(name = "id_rede"))
    @MapKeyColumn(name = "cp")
    private List<String> listaIps = new ArrayList<>();
    
    
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

    public List<String> getListaIps() {
        return listaIps;
    }

    public void setListaIps(List<String> listaIps) {
        Set<String> listaDeIpsUnicos = new TreeSet<String>(new IpComparator());
        listaDeIpsUnicos.addAll(listaIps);
        List<String> list = new ArrayList<>(listaDeIpsUnicos);
        this.listaIps = list;
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
