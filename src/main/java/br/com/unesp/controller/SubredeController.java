package br.com.unesp.controller;

import br.com.unesp.comparator.IpComparator;
import br.com.unesp.dao.SubredeDao;
import br.com.unesp.model.Rede;
import br.com.unesp.model.Subrede;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Query;

@Named
@RequestScoped
public class SubredeController {

    @EJB
    private SubredeDao dao;

    public SubredeDao getDao() {
        return dao;
    }

    public void setDao(SubredeDao dao) {
        this.dao = dao;
    }

    public void salvar() {
        int tamanhoSubrede = 16;
        Query query = this.dao.getEm().createNativeQuery("select enderecoIp from ip where enderecoIp not in (select enderecoIp from subrede_ip)");
        List<String> listaComTodosEnderecoIpDaRede = query.getResultList();
        Collections.sort(listaComTodosEnderecoIpDaRede, new IpComparator());
        SortedSet<String> listaDeEnderecoIpDaSubrede = new TreeSet<String>(new IpComparator());
        listaDeEnderecoIpDaSubrede.addAll(listaComTodosEnderecoIpDaRede.subList(0, tamanhoSubrede));
        
        Rede rede = this.dao.getEm().find(Rede.class, 1);
        Subrede subrede = new Subrede();
        subrede.setRede(rede);
        subrede.setNetmask(subrede.convertTamanhoRedeToNetmask(tamanhoSubrede));
        subrede.setListaEnderecoIpSubrede(listaDeEnderecoIpDaSubrede);
        subrede.setVlan(2);
        this.dao.save(subrede);
        System.out.println("lista todos os ips");
        System.out.println(subrede.getListaEnderecoIpSubrede());
        System.out.println(listaDeEnderecoIpDaSubrede.last());
    }
    
    private String getEnderecoGateway(List<String> listaEnderecoIp) {
        return listaEnderecoIp.get(listaEnderecoIp.size() - 2);
    }
    
    private String getEnderecoGateway(Set<String> listaEnderecoIp) {
        List<String> list = new ArrayList<>(listaEnderecoIp);
        return list.get(listaEnderecoIp.size() - 2);
    }

}
