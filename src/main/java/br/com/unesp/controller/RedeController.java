package br.com.unesp.controller;

import br.com.unesp.dao.RedeDao;
import br.com.unesp.model.Rede;
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
        redeDao.salvar(rede);
        System.out.println("salvando rede ...");
    }
    
}
