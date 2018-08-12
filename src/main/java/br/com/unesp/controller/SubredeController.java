package br.com.unesp.controller;

import br.com.unesp.dao.IpDao;
import br.com.unesp.dao.SubredeDao;
import br.com.unesp.jsf.message.FacesMessages;
import br.com.unesp.model.Ip;
import br.com.unesp.model.Subrede;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SubredeController {

    @Inject
    private SubredeDao dao;
    @Inject
    private IpDao ipdao;
    @Inject
    private FacesMessages message;
    private Subrede subrede = new Subrede();
    private List<Subrede> listaSubrede;
    private Integer quantidadeDeHosts;

    public SubredeDao getDao() {
        return dao;
    }

    public void setDao(SubredeDao dao) {
        this.dao = dao;
    }

    public Subrede getSubrede() {
        return subrede;
    }

    public void setSubrede(Subrede subrede) {
        this.subrede = subrede;
    }

    public List<Subrede> getListaSubrede() {
        return listaSubrede;
    }

    public void setListaSubrede(List<Subrede> listaSubrede) {
        this.listaSubrede = listaSubrede;
    }

    public void salvar() throws Exception {
        this.quantidadeDeHosts = subrede.convertNetmaskToQuantidadeDeHost(subrede.getNetmask());
        List<Ip> ipsDaSubrede = this.getIpsSubrede();
        int quantidadeDeIpsdaSubrede = getQuantidadeDeIpsDaSubrede(ipsDaSubrede);

//        dao.save(subrede);
//        message.info("Subrede salva com sucesso" + this.ipdao.buscarIpsSemVlan(subrede.getRede().getId()));
//        message.info("Subrede salva com sucesso" + ipsDaSubrede);
//        subrede = new Subrede();
        if (ipsDaSubrede.isEmpty() || this.quantidadeDeHosts < quantidadeDeIpsdaSubrede) {
            message.error("Não existem ips suficientes na rede. Nessa rede existem somente " + this.getIpsLivres().size() + " ips livres");
        } else {
            dao.save(subrede);
            message.info("Salvo com sucesso!");
            
        }
    }

    private int getQuantidadeDeIpsDaSubrede(List<Ip> ips) {
        return ips.size();
    }

    private List<Ip> getIpsLivres() {
        try {
            List<Ip> lista = this.ipdao.buscarIpsSemVlan(this.subrede.getRede().getId());
            return lista;
        } catch (Exception e) {
            message.error("Erro ao realizar a consulta de ips livres para subrede");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Ip> getIpsSubrede() {
        try {
            return this.getIpsLivres().subList(0, this.quantidadeDeHosts);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String getIpFinal(List<String> listaEnderecoIp) {
        if (listaEnderecoIp.isEmpty()) {
            return "";
        }
        return listaEnderecoIp.get(listaEnderecoIp.size() - 1);
    }

    public String getEnderecoRede(List<String> listaEnderecoIp) {
        if (listaEnderecoIp.isEmpty()) {
            return "";
        }
        return listaEnderecoIp.get(0);
    }

    public String getEnderecoGateway(List<String> listaEnderecoIp) {
        if (listaEnderecoIp.isEmpty()) {
            return "";
        }
        return listaEnderecoIp.get(listaEnderecoIp.size() - 2);
    }

    private String getEnderecoGateway(Set<String> listaEnderecoIp) {
        List<String> list = new ArrayList<>(listaEnderecoIp);
        return list.get(listaEnderecoIp.size() - 2);
    }

    public Map<String, String> getMarcarasDeRede() {
        Map netmasks = new HashMap();
        netmasks.put("8 hosts - 255.255.255.248", "255.255.255.248");
        netmasks.put("16 hosts - 255.255.255.240", "255.255.255.240");
        netmasks.put("32 hosts - 255.255.255.224", "255.255.255.224");
        netmasks.put("64 hosts - 255.255.255.192", "255.255.255.192");
        netmasks.put("128 hosts - 255.255.255.128", "255.255.255.128");
        netmasks.put("256 hosts - 255.255.255.0", "255.255.255.0");
        return netmasks;
    }
}
