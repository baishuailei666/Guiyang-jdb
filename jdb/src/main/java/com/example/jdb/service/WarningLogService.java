package com.example.jdb.service;


import com.example.jdb.entity.WarningLog;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 异常日志Service层
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
@Service
public interface WarningLogService {

    /**
     * 得到异常日志
     *
     * @param tiId
     * @return
     */
    Map<String, Object> getWarningLogs(String tiId);

    /**
     * 添加异常日志
     *
     * @param warningLog
     * @param tiId
     * @return
     */
    int addWarningLogs(WarningLog warningLog, String tiId);

    /**
     * 删除日志
     *
     * @param id
     * @return
     */
    int deleteWarningLog(int id);

    /**
     * 日志已处理
     *
     * @param id
     * @return
     */
    int updateWarningLog(int id);
}
