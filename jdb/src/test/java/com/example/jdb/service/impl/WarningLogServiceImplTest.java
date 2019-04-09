package com.example.jdb.service.impl;

import com.example.jdb.dao.TerminalDao;
import com.example.jdb.dao.WarningLogDao;
import com.example.jdb.entity.Terminal;
import com.example.jdb.entity.WarningLog;
import com.example.jdb.utils.DateTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.example.jdb.dao")
@Transactional
public class WarningLogServiceImplTest {

    @Autowired
    WarningLogDao warningLogDao;

    @Autowired
    TerminalDao terminalDao;
    @Test
    public void getWarningLogs() {
    }

    @Test
    public void addWarningLogs() throws InterruptedException {
        WarningLog warningLog = new WarningLog();
        warningLog.setMonitorTime(DateTimeUtil.getFormatTime(System.currentTimeMillis()));
        warningLog.setLogPath("D:\\logs");
        Terminal terminal = terminalDao.getTerminal("9844839e4cd14cb89b4248b1221bf9dd");
        warningLog.setTiId(terminal.getTiId());
        warningLog.setmUserName(terminal.getmUserName());
        warningLog.setmUserId(terminal.getmUserId());
        warningLog.setmTime(terminal.getmTime());
        warningLog.setDelFlag(terminal.getDelFlag());
        warningLog.setcUserName(terminal.getcUserName());
        warningLog.setcUserId(terminal.getcUserId());
        warningLog.setcTime(terminal.getcTime());
        warningLog.setLogIsDispose(0);
        for (int i=0; i<10; i++) {
            warningLogDao.addWarningLogs(warningLog);
            Thread.sleep(9000);
            System.out.println("i-" + i);
        }

    }

    @Test
    public void deleteWarningLog() {
    }

    @Test
    public void updateWarningLog() {
    }
}