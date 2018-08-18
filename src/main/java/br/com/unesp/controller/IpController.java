package br.com.unesp.controller;

import br.com.unesp.dao.IpDao;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class IpController {


    @Inject
    private IpDao ipDao;

    public IpDao getIpDao() {
        return ipDao;
    }

    public void setIpDao(IpDao ipDao) {
        this.ipDao = ipDao;
    }

}
