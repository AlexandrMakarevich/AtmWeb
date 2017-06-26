package com.client;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {
        Map<String, Integer> map = new HashMap();
        map.put("1", 1);
        map.put("2", 2);
       Integer value = map.get("3");
        System.out.println(value);
    }
}
