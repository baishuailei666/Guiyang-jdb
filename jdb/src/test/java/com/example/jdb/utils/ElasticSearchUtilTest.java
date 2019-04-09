package com.example.jdb.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ElasticSearchUtilTest {

    @Test
    public void writeToEs() {
        Map<String, Object> map = new HashMap<>(1);

        for (int i=0; i<60; i++) {
            System.out.println("i-" + i);
            map.put("sysCpu", 0.68);
            map.put("useCpu", 0.17);
            map.put("sysMem", 7.8);
            map.put("useMem", 2.3);
            map.put("sysDisk", 420);
            map.put("useDisk", 128 + i);
            map.put("time", DateTimeUtil.getFormatTime(System.currentTimeMillis()));
            map.put("tId", "b62f299cc5ae4b889edaddf615d55879");
            ElasticSearchUtil.writeToEs(map);

        }
    }

    @Test
    public void queryLogFromEs() {
        // dc95cec7a247463ca303153667b5ff71
        System.out.println(ElasticSearchUtil.queryLogFromEs("dc95cec7a247463ca303153667b5ff71",1,10));
    }

    @Test
    public void deleteIndex() {
        ElasticSearchUtil.deleteIndex("fendo");
    }
}