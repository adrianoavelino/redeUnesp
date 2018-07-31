package br.com.unesp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "subrede")
public class Subrede implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subrede")
    private Integer id;
    
    @NotBlank(message = "Selecione uma Máscara de Rede")
    private String netmask;
    
    @Column(name = "enderecoIp", nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "subrede_ip", joinColumns = @JoinColumn(name = "id_subrede"))
    private List<String> listaEnderecoIpSubrede = new ArrayList<>();
    
    @NotNull(message = "Selecione uma Vlan")
    @Column(name = "id_vlan")
    private Integer vlan;
    
    @NotNull(message = "Selecione uma Rede")
    @Column(name = "id_rede")
    private Integer rede;

    public Subrede() {
        this.listaEnderecoIpSubrede = new ArrayList<String>();
    }

    public Subrede(String netmask, List<String> listaEnderecoIpSubrede, Integer vlan) {
        this.netmask = netmask;
        this.listaEnderecoIpSubrede = listaEnderecoIpSubrede;
        this.vlan = vlan;
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

    public List<String> getListaEnderecoIpSubrede() {
        return listaEnderecoIpSubrede;
    }

    public void setListaEnderecoIpSubrede(List<String> listaEnderecoIpSubrede) {
        this.listaEnderecoIpSubrede = listaEnderecoIpSubrede;
    }

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan(Integer vlan) {
        this.vlan = vlan;
    }

    public Integer getRede() {
        return rede;
    }

    public void setRede(Integer rede) {
        this.rede = rede;
    }

    public String converteQuantidadeDeHostsParaNetmask(int tamanhoSubrede) {
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
                mynetmask = "255.255.255.0"; //00000000
                break;
            default:
                mynetmask = "255.255.255.248";
        }
        return mynetmask;
    }

    public int convertNetmaskToQuantidadeDeHost(String netmask) {
        int tamanho;
        switch(netmask) {
                
            case "255.255.255.248":
                tamanho = 8;
                break;
                
            case "255.255.255.240":
                tamanho = 16;
                break;
                
            case "255.255.255.224":
                tamanho = 32;
                break;
                
            case "255.255.255.192":
                tamanho = 64;
                break;
                
            case "255.255.255.128":
                tamanho = 128;
                break;
                
            case "255.255.255.0":
                tamanho = 256;
                break;
            default:
                tamanho = 8;
        }
        return tamanho;
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
        return "Subrede{" + "id=" + id + ", netmask=" + netmask + ", listaEnderecoIpSubrede=" + listaEnderecoIpSubrede + ", vlan=" + vlan + '}';
    }

}
