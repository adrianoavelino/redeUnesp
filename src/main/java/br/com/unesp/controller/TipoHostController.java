package br.com.unesp.controller;

import br.com.unesp.dao.TipoHostDao;
import br.com.unesp.model.TipoHost;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "tipoHostController")
@RequestScoped
public class TipoHostController {

    @EJB
    private TipoHostDao tipoHostDao;

    public TipoHostDao getTipoHostDao() {
        return tipoHostDao;
    }

    public void setTipoHostDao(TipoHostDao tipoHostDao) {
        this.tipoHostDao = tipoHostDao;
    }
    
    
    
    public TipoHostController() {
    }
    
        
    public void salvar() {
        TipoHost tipoHost = new TipoHost("Computador");
        tipoHostDao.salvar(tipoHost);
        System.out.println("salvando tipo host ...");
    }
    
}
