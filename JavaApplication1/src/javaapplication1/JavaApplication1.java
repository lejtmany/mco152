/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

class ZigWig {

    ZigWig(String s) {
        name = s;
    }
    String name;
    int id;
    
    @Override
    public String toString()
    {
        return name + ": " + id;
    }
}

public class JavaApplication1 {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        
        list.addAll( Arrays.asList(
                new String []{"Bob", "Jim", "Eli", "Harold", "Avrohom","Yitzchak"}));
        
        for(String s: list)
        {
            System.out.println(s);
        }
        
        for (   Iterator<String> iterator = list.iterator();
                iterator.hasNext();iterator.next())
                
        {
            //String s = iterator.next();
            System.out.println(iterator.next());
        }
    }

}
