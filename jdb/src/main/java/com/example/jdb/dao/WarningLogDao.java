package com.example.jdb.dao;

import com.example.jdb.entity.WarningLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 异常日志Dao层
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
@Service
public interface WarningLogDao {

    /**
     * 得到异常日志
     *
     * @param tiId 终端ID
     * @return List
     */
    @Select("SELECT * FROM cf_log_warninglog WHERE ti_id = #{tiId}")
    @Results({
            @Result(property = "pkId", column = "pk_id"),
            @Result(property = "monitorTime", column = "monitor_time"),
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "logIsDispose", column = "log_isdispose"),
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),
    })
    List<WarningLog> getWarningLogs(@Param("tiId") String tiId);

    /**
     * 下载异常日志
     *
     * @param pkId 主键ID
     * @return WarningLog
     */
    @Select("SELECT * FROM cf_log_warninglog WHERE pk_id = #{pkId}")
    @Results({
            @Result(property = "pkId", column = "pk_id"),
            @Result(property = "monitorTime", column = "monitor_time"),
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "logIsDispose", column = "log_isdispose"),
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),
    })
    WarningLog getWarningLogsById(@Param("pkId") int pkId);

    /**
     * 添加异常日志
     *
     * @param warningLog warningLog
     * @return int
     */
    @Insert("INSERT INTO cf_log_warninglog(monitor_time, log_path, log_isdispose, ti_id " +
            ",c_user_id ,c_user_name ,m_user_id ,m_user_name ,c_time ,m_time, del_flag) " +
            "VALUES(#{monitorTime},#{logPath},#{logIsDispose},#{tiId},#{cUserId} " +
            ",#{cUserName},#{mUserId},#{mUserName},#{cTime},#{mTime},#{delFlag})")
    @Results({
            @Result(property = "monitorTime", column = "monitor_time"),
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "logIsDispose", column = "log_isdispose"),
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),
    })
    int addWarningLogs(WarningLog warningLog);

    /**
     * 删除日志
     *
     * @param id
     * @return int
     */
    @Delete("DELETE FROM cf_log_warninglog WHERE id = #{id}")
    int deleteWarningLog(@Param("id") int id);

    /**
     * 日志已处理
     *
     * @param id
     * @return int
     */
    @Update("UPDATE cf_log_warninglog SET log_isdispose = 1 WHERE id = #{id}")
    int updateWarningLog(@Param("id") int id);
}
