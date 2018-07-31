/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.unesp.model;

import br.com.unesp.comparator.IpComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author adriano
 */
public class Teste {
    
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        
        for (int i = 0; i < 255; i++) {
            set.add("200.145.44."+i);
        }
        
        
        List<String> list = new ArrayList<String>(set);
        Collections.sort(list, new IpComparator());
        List<String> subList = list.subList(0, 5);
        
        SortedSet<String> sub = new TreeSet<>(new IpComparator());
        sub.addAll(subList);
        
        System.out.println("sub com sortset" + sub);
        System.out.println("sublist" + subList);
        System.out.println("list" + list);
    }
}
