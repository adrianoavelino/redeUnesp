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
    private Vlan vlan;
    private Ip ip;
    private List<Ip> listaDeIps;
    private List<Vlan> vlans;

    public HostController() {
        listaDeIps = new ArrayList<>();
        vlans = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        this.carregarVlans();
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

    public List<Vlan> getVlans() {
        return vlans;
    }

    public void setVlans(List<Vlan> vlans) {
        this.vlans = vlans;
    }

    public void salvar() {
        if (host.getId() == null) {
            if (this.validate()) {
                dao.salvar(this.host);
                message.info("Salvo com sucesso!");
                host = new Host();
                vlan = null;
                this.listaDeIps = null;
            }
        } else {
            if (this.validate()) {
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

    private boolean validate() {
        if (vlan != null && host.getIp() == null) {
            if (ip == null) {
                message.error("Selecione um IP");
                return false;
            }
        }
        return true;
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
        if (host.getIp() != null) {
            Long idDoIp = host.getIp().getId();
            vlan = vlanDao.buscarPorIp(idDoIp);
            listaDeIps = ipDao.buscarIpsDaVlan(vlan.getId());
            listaDeIps.add(host.getIp());
        }
    }

    public void onSelectVlan() {
        if (vlan == null) {
            this.listaDeIps = null;
            return;
        }
        try {
            int idDaVlan = vlan.getId();
            this.listaDeIps = ipDao.buscarIpsDaVlan(idDaVlan);
        } catch (Exception ex) {
            message.error("Erro ao selecionar os ips da vlan " + vlan);
            ex.printStackTrace();
        }
    }

    public String buscarDescricaoDaVlan(Long idDoIp) {
        return vlanDao.buscarPorIp(idDoIp).getDescricao();
    }
}
