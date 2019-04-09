package com.example.jdb.service.impl;

import com.example.jdb.dao.MonitorHistoryDao;
import com.example.jdb.dao.TerminalDao;
import com.example.jdb.entity.MonitoringHistory;
import com.example.jdb.entity.Terminal;
import com.example.jdb.service.MonitorHistoryService;
import com.example.jdb.utils.DateTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.example.jdb.dao")
public class MonitorHistoryServiceImplTest {
    @Autowired
    MonitorHistoryService monitorHistoryService;
    @Autowired
    MonitorHistoryDao monitorHistoryDao;
    @Autowired
    TerminalDao terminalDao;

    @Test
    public void addMonitorHistory() {
        Map<String, Object> map = new HashMap<>(1);
        String[] tids = {"b62f299cc5ae4b889edaddf615d55879", "dc95cec7a247463ca303153667b5ff71", "7ff8ccb42ef646afb61008d99ed16fbc"};

        for (int i=0; i<50; i++) {

            map.put("sysCpu", BigDecimal.valueOf(0.68));
            map.put("useCpu", BigDecimal.valueOf(0.27));
            map.put("sysMem", BigDecimal.valueOf(7.8));
            map.put("useMem", BigDecimal.valueOf(2.3));
            map.put("sysDisk", BigDecimal.valueOf(420));
            map.put("useDisk", BigDecimal.valueOf(128 + i));
            map.put("time", DateTimeUtil.getFormatTime(System.currentTimeMillis()));
            for (int j=0; j<=2; j++) {
                map.put("tiId", tids[j]);
                System.out.println(monitorHistoryService.addMonitorHistory(map));
                System.out.println("i-" + i);
            }
        }
    }

    @Test
    public void getMonitoringHistory() {
        System.out.println(terminalDao.getList("3f4d7216c74b4f2f9cf564868f974ee7","",1,10));
    }

    @Test
    public void getDetailInfo() {
        System.out.println(monitorHistoryService.getDetailInfo("7ff8ccb42ef646afb61008d99ed16fbc"));
    }

    @Test
    public void getMonitorDetailInfo() {
        System.out.println(monitorHistoryService.getMonitorDetailInfo(3,"7ff8ccb42ef646afb61008d99ed16fbc"));
    }

    @Test
    public void getListMonitoringHistory() {
        MonitoringHistory monitoringHistory = new MonitoringHistory();
        monitoringHistory.setTiId("dc95cec7a247463ca303153667b5ff71");
        monitoringHistory.setMonitorTime(DateTimeUtil.getFormatTime(System.currentTimeMillis()));
        monitoringHistory.setSysCpu(BigDecimal.valueOf(0.6742));
        monitoringHistory.setUseCpu(BigDecimal.valueOf(0.2164));
        monitoringHistory.setSysMem(BigDecimal.valueOf(7.8));
        monitoringHistory.setUseMem(BigDecimal.valueOf(3.6));
        monitoringHistory.setSysDisk(BigDecimal.valueOf(300));
        monitoringHistory.setUseDisk(BigDecimal.valueOf(116));

        monitorHistoryDao.addMonitorHistory(monitoringHistory);
    }

}