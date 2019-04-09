package com.example.jdb.dao.mapper;

import org.junit.Test;

import static org.junit.Assert.*;

public class MonitorHistoryMapperTest {

    @Test
    public void getListMonitoringHistoryByTiId() {
        System.out.println(MonitorHistoryMapper.getListMonitoringHistoryByTiId("dc95cec7a247463ca303153667b5ff71","",1,10));
    }
}