package br.com.unesp.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "host")
public class Host implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_host")
    private Integer id;
    @NotBlank(message = "O campo nome está vazio")
    private String nome;
    @NotBlank(message = "O campo Mac-address está vazio")
    private String macAddress;
    @OneToOne
    @JoinColumn(name = "id_tipo_host")
    @NotNull(message = "Selecione um tipo de host")
    private TipoHost tipo;
    
    @OneToOne
    private Ip ip;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Host() {
    }

    public Host(String nome, String macAddress, TipoHost tipo) {
        this.nome = nome;
        this.macAddress = macAddress;
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

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public TipoHost getTipo() {
        return tipo;
    }

    public void setTipo(TipoHost tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ip getIp() {
        return ip;
    }

    public void setIp(Ip ip) {
        this.ip = ip;
    }

    public boolean isDiferente(Host host) {
        return this.id != host.getId();
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
        return "Host{" + "id=" + id + ", nome=" + nome + ", macAddress=" + macAddress + ", tipo=" + tipo + ", ip=" + ip + ", usuario=" + usuario + '}';
    }

}
