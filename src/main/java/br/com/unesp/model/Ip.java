package br.com.unesp.model;

import java.util.ArrayList;
import java.util.List;

public class Ip {

   public static List<String> criarListaEnderecoIp(Rede rede) {
        List<String> ips = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            ips.add(rede.getEndereco() + "." + i);
        }
        ips.add(rede.getEndereco() + "." + 255);
        return ips;
    }    
    
}
