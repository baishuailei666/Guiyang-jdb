package com.example.jdb.dao;

import com.example.jdb.dao.mapper.MonitorHistoryMapper;
import com.example.jdb.entity.MonitoringHistory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 设备运行占用系统资源状况Dao层
 *
 * @author 白帅雷
 * @date 2019-01-09
 */
@Service
public interface MonitorHistoryDao {

    /**
     * 添加 设备运行占用资源状况
     *
     * @param monitoringHistory 资源监控实体对象
     * @return int
     */
    @Insert("INSERT INTO cf_ti_monitoring_history(ti_id, monitor_time, sys_cpu, use_cpu" +
            ", sys_mem, use_mem, sys_disk, use_disk, c_user_id, c_user_name, m_user_id" +
            ", m_user_name, c_time, m_time, del_flag) " +
            "VALUES(#{tiId}, #{monitorTime}, #{sysCpu}, #{useCpu}, #{sysMem}" +
            "      , #{useMem}, #{sysDisk}, #{useDisk}, #{cUserId}, #{cUserName}, #{mUserId}" +
            "      , #{mUserName}, #{cTime}, #{mTime}, #{delFlag})")
    int addMonitorHistory(MonitoringHistory monitoringHistory);

    @Delete("DELETE * FROM cf_ti_monitoring_history WHERE monitor_time < DATE_SUB(CURDATE(), INTERVAL -3 MONTH)")
    int deleteMonitorHistory();

    /**
     * 查看 设备运行占用资源状况(默认当天)
     *
     * @param tiId 终端ID
     * @return MonitoringHistory
     */
    @Select("SELECT * FROM cf_ti_monitoring_history WHERE TO_DAYS(NOW()) - TO_DAYS(monitor_time)" +
            " = 0 AND ti_id = #{tiId} ORDER BY monitor_time DESC ")
    @Results({
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "monitorTime", column = "monitor_time"),
            @Result(property = "sysCpu", column = "sys_cpu"),
            @Result(property = "useCpu", column = "use_cpu"),
            @Result(property = "sysMem", column = "sys_mem"),
            @Result(property = "useMem", column = "use_mem"),
            @Result(property = "sysDisk", column = "sys_disk"),
            @Result(property = "useDisk", column = "use_disk"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),

    })
    List<MonitoringHistory> getMonitoringHistory(@Param("tiId") String tiId);

    /**
     * 查看 设备运行占用资源状况(条件：近三天、近七天)
     *
     * @param tiId 终端ID
     * @param day 查询天数
     * @return MonitoringHistory
     */
    @Select("SELECT * FROM cf_ti_monitoring_history WHERE TO_DAYS(NOW()) - TO_DAYS(monitor_time)" +
            " <= #{day} AND ti_id = #{tiId} ORDER BY monitor_time DESC ")
    @Results({
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "monitorTime", column = "monitor_time"),
            @Result(property = "sysCpu", column = "sys_cpu"),
            @Result(property = "useCpu", column = "use_cpu"),
            @Result(property = "sysMem", column = "sys_mem"),
            @Result(property = "useMem", column = "use_mem"),
            @Result(property = "sysDisk", column = "sys_disk"),
            @Result(property = "useDisk", column = "use_disk"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),

    })
    List<MonitoringHistory> getMonitoringHistoryDay(@Param("day")int day, @Param("tiId") String tiId);

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @return List
//     */
//    @Select("SELECT * FROM cf_ti_monitoring_history, cf_ti_terminal WHERE " +
//            "cf_ti_monitoring_history.ti_id = cf_ti_terminal.ti_id " +
//            "AND cf_ti_monitoring_history.ti_id = #{tiId} AND cf_ti_terminal.del_flag !=1 " +
//            "ORDER BY cf_ti_monitoring_history.monitor_time DESC LIMIT #{num}, #{limit}")
    @Results({
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "monitorTime", column = "monitor_time"),
            @Result(property = "sysCpu", column = "sys_cpu"),
            @Result(property = "useCpu", column = "use_cpu"),
            @Result(property = "sysMem", column = "sys_mem"),
            @Result(property = "useMem", column = "use_mem"),
            @Result(property = "sysDisk", column = "sys_disk"),
            @Result(property = "useDisk", column = "use_disk"),
            @Result(property = "hiId", column = "hi_id"),
            @Result(property = "tiSwId", column = "ti_sw_id"),
            @Result(property = "tiCode", column = "ti_code"),
            @Result(property = "bhId", column = "bh_id"),
            @Result(property = "tiType", column = "ti_type"),
            @Result(property = "tiUpTime", column = "ti_up_time"),
            @Result(property = "tiDownTime", column = "ti_down_time"),
            @Result(property = "tiUserId", column = "ti_user_id"),
            @Result(property = "tiUserName", column = "ti_user_name"),
            @Result(property = "emMode", column = "em_mode"),
            @Result(property = "emStatus", column = "em_status"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),
    })
    @SelectProvider(type = MonitorHistoryMapper.class, method = "getListMonitoringHistory")
    List<MonitoringHistory> getListMonitoringHistory(@Param("tiId") String tiId, @Param("num") int num, @Param("limit") int limit);

    /**
     * count
     *
     * @return List
     */
    @Select("SELECT COUNT(*) FROM cf_ti_monitoring_history, cf_ti_terminal WHERE " +
            "cf_ti_monitoring_history.ti_id = cf_ti_terminal.ti_id " +
            "AND cf_ti_monitoring_history.ti_id = #{tiId} AND cf_ti_terminal.del_flag !=1")
    int getListMonitoringHistoryCount(@Param("tiId") String tiId);

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @return List
     */
//    @Select("SELECT * FROM cf_ti_monitoring_history, cf_ti_terminal WHERE " +
//            "cf_ti_monitoring_history.ti_id = cf_ti_terminal.ti_id " +
//            "AND cf_ti_monitoring_history.ti_id = #{tiId} AND cf_ti_terminal.del_flag !=1 " +
//            "ORDER BY cf_ti_monitoring_history.monitor_time DESC LIMIT #{num}, 20")
    @Results({
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "monitorTime", column = "monitor_time"),
            @Result(property = "sysCpu", column = "sys_cpu"),
            @Result(property = "useCpu", column = "use_cpu"),
            @Result(property = "sysMem", column = "sys_mem"),
            @Result(property = "useMem", column = "use_mem"),
            @Result(property = "sysDisk", column = "sys_disk"),
            @Result(property = "useDisk", column = "use_disk"),
            @Result(property = "hiId", column = "hi_id"),
            @Result(property = "tiSwId", column = "ti_sw_id"),
            @Result(property = "tiCode", column = "ti_code"),
            @Result(property = "bhId", column = "bh_id"),
            @Result(property = "tiType", column = "ti_type"),
            @Result(property = "tiUpTime", column = "ti_up_time"),
            @Result(property = "tiDownTime", column = "ti_down_time"),
            @Result(property = "tiUserId", column = "ti_user_id"),
            @Result(property = "tiUserName", column = "ti_user_name"),
            @Result(property = "emMode", column = "em_mode"),
            @Result(property = "emStatus", column = "em_status"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),
    })
    @SelectProvider(type = MonitorHistoryMapper.class, method = "getListMonitoringHistoryByTiId")
    List<MonitoringHistory> getListMonitoringHistoryByTiId(@Param("tiId") String tiId, @Param("emStatus") String emStatus ,@Param("num") int num, @Param("limit") int limit);

    /**
     * count
     *
     * @return List
     */
    @SelectProvider(type = MonitorHistoryMapper.class, method = "getListMonitoringHistoryByTiIdCount")
    int getListMonitoringHistoryByTiIdCount(@Param("tiId") String tiId, @Param("emStatus") String emStatus);
}
