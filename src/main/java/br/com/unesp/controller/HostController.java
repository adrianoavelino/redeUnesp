package br.com.unesp.controller;

import br.com.unesp.dao.HostDao;
import br.com.unesp.dao.IpDao;
import br.com.unesp.dao.Ipv6Dao;
import br.com.unesp.dao.VlanDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Host;
import br.com.unesp.model.Ip;
import br.com.unesp.model.Ipv6;
import br.com.unesp.model.TipoHost;
import br.com.unesp.model.Usuario;
import br.com.unesp.model.Vlan;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "hostController")
@ViewScoped
public class HostController implements Serializable {

    @Inject
    private HostDao dao;
    @Inject
    private IpDao ipDao;
    @Inject
    private Ipv6Dao ipv6Dao;
    @Inject
    private VlanDao vlanDao;
    @Inject
    private FacesMessages message;
    private Host host = new Host();
    private List<Object[]> hosts;
    private Vlan vlan;
    private Ip ip;
    private List<Ip> listaDeIps;
    private List<Ipv6> listaDeIpv6s;
    private List<Vlan> vlans;

    public HostController() {
        listaDeIps = new ArrayList<>();
        listaDeIpv6s = new ArrayList<>();
        vlans = new ArrayList<>();
    }

    public HostDao getDao() {
        return dao;
    }

    public void setDao(HostDao dao) {
        this.dao = dao;
    }

    public IpDao getIpDao() {
        return ipDao;
    }

    public void setIpDao(IpDao ipDao) {
        this.ipDao = ipDao;
    }

    public Ipv6Dao getIpv6Dao() {
        return ipv6Dao;
    }

    public void setIpv6Dao(Ipv6Dao ipv6Dao) {
        this.ipv6Dao = ipv6Dao;
    }
    
    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public List<Object[]> getHosts() {
        try {
            hosts = dao.listar();
        } catch (Exception ex) {
            message.error("Erro ao listar os hosts ");
        }
        return hosts;
    }

    public void setHosts(List<Object[]> hosts) {
        this.hosts = hosts;
    }

    public Vlan getVlan() {
        return vlan;
    }

    public void setVlan(Vlan vlan) {
        this.vlan = vlan;
    }

    public Ip getIp() {
        return ip;
    }

    public void setIp(Ip ip) {
        this.ip = ip;
    }

    public List<Ip> getListaDeIps() {
        return listaDeIps;
    }

    public void setListaDeIps(List<Ip> listaDeIps) {
        this.listaDeIps = listaDeIps;
    }

    public List<Ipv6> getListaDeIpv6s() {
        return listaDeIpv6s;
    }

    public void setListaDeIpv6s(List<Ipv6> ipv6s) {
        this.listaDeIpv6s = ipv6s;
    }
    
    public List<Vlan> getVlans() {
        return vlans;
    }

    public void setVlans(List<Vlan> vlans) {
        this.vlans = vlans;
    }

    public void salvar() {
        if (host.getId() == null) {
            if (this.validar()) {
                dao.salvar(this.host);
                message.info("Salvo com sucesso!");
                host = new Host();
                vlan = null;
                this.listaDeIps = null;
            }
        } else {
            if (this.validar()) {
                try {
                    dao.atualizar(this.host);
                    message.info("Atualizado com sucesso!");
                    host = new Host();
                    vlan = null;
                    this.carregarVlans();
                    this.listaDeIps = null;
                } catch (Exception ex) {
                    message.error("Erro ao atualizar host");
                }
            }
        }
    }

    private void carregarVlans() {
        try {
            vlans = vlanDao.listar();
        } catch (Exception ex) {
            message.error("Erro ao listar vlans");
        }
    }

    public void deletar(ActionEvent evento) {
        Object[] hostSelecionado = (Object[]) evento.getComponent().getAttributes().get("hostSelecionado");

        TipoHost tipo = new TipoHost();
        tipo.setId((Integer) hostSelecionado[7]);
        tipo.setTipo((String) hostSelecionado[3]);
        Usuario usuario = new Usuario();
        usuario.setId((Integer) hostSelecionado[8]);
        usuario.setNome((String) hostSelecionado[4]);

        host.setId((Integer) hostSelecionado[0]);
        host.setNome((String) hostSelecionado[1]);
        host.setMacAddress((String) hostSelecionado[2]);
        host.setTipo(tipo);
        host.setUsuario(usuario);
        try {
            dao.deletar(host);
        } catch (Exception ex) {
            message.error("Erro ao deletar tipo do host");
        }
    }

    public void editar(ActionEvent evento) {
        Object[] hostSelecionado = (Object[]) evento.getComponent().getAttributes().get("hostSelecionado");

        TipoHost tipo = new TipoHost();
        tipo.setId((Integer) hostSelecionado[7]);
        tipo.setTipo((String) hostSelecionado[3]);
        Usuario usuario = new Usuario();
        usuario.setId((Integer) hostSelecionado[8]);
        usuario.setNome((String) hostSelecionado[4]);

        host.setId((Integer) hostSelecionado[0]);
        host.setNome((String) hostSelecionado[1]);
        host.setMacAddress((String) hostSelecionado[2]);
        host.setTipo(tipo);
        host.setUsuario(usuario);

        if (hostSelecionado[9] != null) {
            BigInteger idDoIp = (BigInteger) hostSelecionado[9];
            Ip ipLocal = ipDao.buscarIpPorId(idDoIp.longValue());
            host.setIp(ipLocal);
            vlan = vlanDao.buscarPorIp(ipLocal.getId());
            listaDeIps = ipDao.buscarIpsDaVlan(vlan.getId());
            listaDeIps.add(host.getIp());
            return;
        }

        if (hostSelecionado[11] != null) {
            BigInteger idDoIp = (BigInteger) hostSelecionado[9];
            BigInteger idDoIpv6 = (BigInteger) hostSelecionado[11];
            Ipv6 ipv6Local = ipv6Dao.buscarIpv6PorId(idDoIpv6.longValue());
            host.setIpv6(ipv6Local);
            vlan = vlanDao.buscarPorIp(idDoIp.longValue());
            listaDeIpv6s = ipv6Dao.buscarIpv6PorVlan(vlan.getId());
            listaDeIpv6s.add(host.getIpv6());
            return;
        } 
        host.setIpv6(null);
        host.setIp(null);
        this.vlan = null;
    }

    public void onSelectVlan() {
        if (vlan == null) {
            this.listaDeIps = null;
            this.listaDeIpv6s = null;
            return;
        }
        try {
            int idDaVlan = vlan.getId();
            this.listaDeIps = ipDao.buscarIpsDaVlan(idDaVlan);
            this.listaDeIpv6s = ipv6Dao.buscarIpv6PorVlan(idDaVlan);
        } catch (Exception ex) {
            message.error("Erro ao selecionar os ips da vlan " + vlan);
            ex.printStackTrace();
        }
    }

    public boolean validar() {
        boolean temErro = true;
        if (dao.isNomeDuplicado(host)) {
            message.error("O nome já está cadastrado");
            temErro = false;
        }
        if (dao.isMacAddressDuplicado(host)) {
            message.error("Mac-address já está cadastrado");
            temErro = false;
        }

        if (!this.validateIpv4()) {
            message.error("Selecione um endereço IPV4");
            temErro = false;
        }

        if (!this.validateIpv6()) {
            message.error("Selecione um endereço IPV6");
            temErro = false;
        }

        return temErro;
    }

    private boolean validateIpv4() {
        if (vlan != null && host.getIp() == null) {
            if (ip == null) {
                return false;
            }
        }
        return true;
    }

    private boolean validateIpv6() {
        if (vlan != null && host.getIpv6() == null) {
            if (ip == null) {
                return false;
            }
        }
        return true;
    }
    
}
