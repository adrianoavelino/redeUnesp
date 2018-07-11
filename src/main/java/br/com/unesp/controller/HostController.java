package br.com.unesp.controller;

import br.com.unesp.dao.HostDao;
import br.com.unesp.model.Host;
import br.com.unesp.model.TipoHost;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value = "hostController")
@RequestScoped
public class HostController {
    
    @EJB
    private HostDao hostDao;

    public HostDao getHostDao() {
        return hostDao;
    }

    public void setHostDao(HostDao hostDao) {
        this.hostDao = hostDao;
    }
    
    public void salvar() {
        TipoHost tipoHost = new TipoHost();
        tipoHost.setId(1);
        Host host = new Host("Lee", "aa:aa:aa:aa", tipoHost);
        hostDao.salvar(host);
        System.out.println("salvando host ...");
    }
}
