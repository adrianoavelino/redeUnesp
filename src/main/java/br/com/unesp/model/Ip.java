package br.com.unesp.model;

import java.util.HashSet;
import java.util.Set;

public class Ip {

   public static Set<String> criarListaEnderecoIp(Rede rede) {
        Set<String> ips = new HashSet<>();
        for (int i = 0; i < 256; i++) {
            ips.add(rede.getEndereco() + "." + i);
        }
        return ips;
    }    
    
}
