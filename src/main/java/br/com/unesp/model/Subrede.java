package br.com.unesp.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "subrede")
public class Subrede implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subrede")
    private Integer id;
    private String netmask;
    @Column(name = "enderecoIp", nullable = false)
    @ElementCollection
    @CollectionTable(name = "subrede_ip", joinColumns = @JoinColumn(name = "id_subrede"))
    private Set<String> listaEnderecoIpSubrede;
    @Column(name = "id_vlan")
    private Integer vlan;
    @OneToOne
    @JoinColumn(name = "id_rede")
    private Rede rede;

    public Subrede() {
        this.listaEnderecoIpSubrede = new HashSet<String>();
    }

    public Subrede(String netmask, Set<String> listaEnderecoIpSubrede, Integer vlan, Rede rede) {
        this.netmask = netmask;
        this.listaEnderecoIpSubrede = listaEnderecoIpSubrede;
        this.vlan = vlan;
        this.rede = rede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public Set<String> getListaEnderecoIpSubrede() {
        return listaEnderecoIpSubrede;
    }

    public void setListaEnderecoIpSubrede(Set<String> listaEnderecoIpSubrede) {
        this.listaEnderecoIpSubrede = listaEnderecoIpSubrede;
    }

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan(Integer vlan) {
        this.vlan = vlan;
    }

    public String convertTamanhoRedeToNetmask(int tamanhoSubrede) {
        String mynetmask = null;
        switch(tamanhoSubrede) {
                
            case 8:
                mynetmask = "255.255.255.248"; //11111000
                break;
                
            case 16:
                mynetmask = "255.255.255.240"; //11110000
                break;
                
            case 32:
                mynetmask = "255.255.255.224"; //11100000
                break;
                
            case 64:
                mynetmask = "255.255.255.192"; //11000000
                break;
                
            case 128:
                mynetmask = "255.255.255.128"; //10000000
                break;
                
            case 256:
                mynetmask = "255.255.255.255"; //00000000
                break;
            default:
                mynetmask = "255.255.255.248";
        }
        return mynetmask;
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final Subrede other = (Subrede) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Subrede{" + "id=" + id + ", netmask=" + netmask + ", vlan=" + vlan + '}';
    }

}
