package br.com.unesp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "ip")
public class Ip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ip_id")
    private Long id;
    private String enderecoIp;
    @ManyToOne
    @JoinColumn(name = "rede_id")
    private Rede rede;
    @OneToOne(mappedBy = "ip")
    private Host host;

    public Ip() {
    }

    public Ip(String enderecoIp, Rede rede) {
        this.enderecoIp = enderecoIp;
        this.rede = rede;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnderecoIp() {
        return enderecoIp;
    }

    public void setEnderecoIp(String enderecoIp) {
        this.enderecoIp = enderecoIp;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Ip other = (Ip) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ip{" + "id=" + id + ", enderecoIp=" + enderecoIp + ", rede=" + rede + '}';
    }

    public static List<Ip> criarListaEnderecoIp(Rede rede) {
        List<Ip> ips = new ArrayList<>();
        Ip ip;
        String enderecoIp;
        for (int i = 0; i < 256; i++) {
            enderecoIp = "" + i;
            ip = new Ip(enderecoIp, rede);
            ips.add(ip);
        }
        return ips;
    }

}
