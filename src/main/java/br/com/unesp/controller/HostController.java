package br.com.unesp.controller;

import br.com.unesp.dao.HostDao;
import br.com.unesp.dao.IpDao;
import br.com.unesp.dao.VlanDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Host;
import br.com.unesp.model.Ip;
import br.com.unesp.model.Vlan;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private VlanDao vlanDao;
    @Inject
    private FacesMessages message;
    private Host host = new Host();
    private Collection<Host> hosts;
    private Integer vlan;
    private String ip;
    private List<Ip> listaDeIps;
    private List<Vlan> vlans;

    public HostController() {
        listaDeIps = new ArrayList<>();
        vlans = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        try {
            vlans = vlanDao.listar();
        } catch (Exception ex) {
            message.error("erro ao listar vlans");
        }
        host.setNome("pc");
        host.setMacAddres("11:11");
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

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Collection<Host> getHosts() {
        try {
            hosts = dao.listar();
        } catch (Exception ex) {
            message.error("Erro ao listar os hosts ");
        }
        return hosts;
    }

    public void setHosts(Collection<Host> hosts) {
        this.hosts = hosts;
    }

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan(Integer vlan) {
        this.vlan = vlan;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Ip> getListaDeIps() {
        return listaDeIps;
    }

    public void setListaDeIps(List<Ip> listaDeIps) {
        this.listaDeIps = listaDeIps;
    }

    public List<Vlan> getVlans() {
        return vlans;
    }

    public void setVlans(List<Vlan> vlans) {
        this.vlans = vlans;
    }

    public void salvar() {
        if (host.getId() == null) {
            if (vlan != null && ip == null) {
                if (ip == null) {
                    message.error("Selecione um IP");
                }
            } else {
                host.setEnderecoIp(ip);
//                dao.salvar(this.host);
                message.info("Salvo com sucesso!" + host);
//                host = new Host();
            }
        } else {
            if (vlan != null && ip == null) {
                message.error("Selecione um IP");
            } else {
                host.setEnderecoIp(ip);
                try {
                    dao.atualizar(this.host);
                } catch (Exception ex) {
                    message.error("erro ao atualizar host");
                }
                message.info("Atualiza" + host);
                host = new Host();
                try {
                    vlans = vlanDao.listar();
                } catch (Exception ex) {
                    message.error("Erro ao listar vlans");
                }
            }
        }
    }

    public void deletar(ActionEvent evento) {
        host = (Host) evento.getComponent().getAttributes().get("hostSelecionado");
        try {
            dao.deletar(host);
        } catch (Exception ex) {
            message.error("Erro ao deletar tipo do host");
        }
    }

    public void editar(ActionEvent evento) {
        host = (Host) evento.getComponent().getAttributes().get("hostSelecionado");
    }

    public void onSelectVlan() {
        try {
            listaDeIps = ipDao.buscarIpsDaVlan(vlan);
        } catch (Exception ex) {
            message.error("Erro ao selecionar os ips da vlan " + vlan);
            ex.printStackTrace();
        }
    }

//    public void onSelectIp() {
//        host.setEnderecoIp(ip);
//    }
//    public void testar() {
//        message.info("testar");
//    }
}
