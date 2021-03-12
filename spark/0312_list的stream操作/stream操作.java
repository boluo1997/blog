package com.landeli.hatchery.table;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class boluoTest01 {

    @Test
    public void testMap(){
        Map<String, String> map = new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");

        map.forEach((k, v)->{
            System.out.println(" "+k);
            System.out.println(" "+v);
        });
    }

    @Test
    public void testList(){
        List<String> list = new ArrayList<>();
        list.add("list1");
        list.add("list2");
        list.add("list3");

        list.stream().map(str -> {
            return str.substring(0,str.indexOf("s"));
        }).forEach(
                str -> System.out.println(str)
        );

        list.stream()
                .filter(str -> str.equals("list2"))
                .map(str -> {
                    return str.concat("concat");
                }).forEach(
                    str -> System.out.println(str)
        );

        List list2 = list.stream().map(str -> {
            return str + "qww";
        }).collect(Collectors.toList());
        System.out.println(list2);

    }

}





