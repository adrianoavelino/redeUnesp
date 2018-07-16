package br.com.unesp.controller;

import br.com.unesp.dao.RedeDao;
import br.com.unesp.model.Ip;
import br.com.unesp.model.Rede;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "redeController")
@RequestScoped
public class RedeController {

    @EJB
    private RedeDao redeDao;

    public RedeDao getRedeDao() {
        return redeDao;
    }

    public void setRedeDao(RedeDao redeDao) {
        this.redeDao = redeDao;
    }
    
    public RedeController() {
    }
    
        
    public void salvar() {
        Rede rede = new Rede("200.145.44");
        List<String> list = Ip.criarListaEnderecoIp(rede);
        rede.setListaIps(list);
        redeDao.salvar(rede);
        System.out.println("salvando rede ...");
    }
    
}
