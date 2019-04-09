package com.example.jdb.service.impl;

import com.example.jdb.dao.TerminalDao;
import com.example.jdb.dao.WarningLogDao;
import com.example.jdb.entity.Terminal;
import com.example.jdb.entity.WarningLog;
import com.example.jdb.service.WarningLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常日志ServiceImpl层
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
@Service
public class WarningLogServiceImpl implements WarningLogService {

    @Autowired
    private WarningLogDao warningLogDao;
    @Autowired
    private TerminalDao terminalDao;
    /**
     * 得到异常日志
     *
     * @param tiId
     * @return
     */
    @Override
    public Map<String, Object> getWarningLogs(String tiId) {
        Map<String, Object> map = new HashMap<>(1);
        List<WarningLog> list = warningLogDao.getWarningLogs(tiId);
        list.forEach(warningLog -> {
            warningLog.setMonitorTime(warningLog.getMonitorTime().substring(0, warningLog.getMonitorTime().length() - 2));
        });
        map.put("warningLogs", list);
        return map;
    }

    /**
     * 添加异常日志
     *
     * @param warningLog
     * @param tiId
     * @return
     */
    @Override
    public int addWarningLogs(WarningLog warningLog, String tiId) {
        Terminal terminalLog = terminalDao.getTerminal(tiId);
        warningLog.setcTime(terminalLog.getcTime());
        warningLog.setDelFlag(terminalLog.getDelFlag());
        warningLog.setmTime(terminalLog.getmTime());
        warningLog.setcUserId(terminalLog.getcUserId());
        warningLog.setcUserName(terminalLog.getcUserName());
        warningLog.setmUserId(terminalLog.getmUserId());
        warningLog.setmUserName(terminalLog.getmUserName());

        return warningLogDao.addWarningLogs(warningLog);
    }

    /**
     * 删除日志
     *
     * @param id
     * @return
     */
    @Override
    public int deleteWarningLog(int id) {
        return warningLogDao.deleteWarningLog(id);
    }

    /**
     * 日志已处理
     *
     * @param id
     * @return
     */
    @Override
    public int updateWarningLog(int id) {
        return warningLogDao.updateWarningLog(id);
    }
}
