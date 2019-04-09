package com.example.jdb.dao;


import com.example.jdb.entity.Terminal;
import com.example.jdb.dao.mapper.TerminalMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 终端Dao层
 *
 * @author 白帅雷
 * @date 2019-01-09
 */
@Service
public interface TerminalDao {

    /**
     * 获取对应tiId的终端信息
     *
     * @param tiId 终端ID
     * @return Terminal
     */
    @Select("SELECT * FROM cf_ti_terminal WHERE ti_id = #{tiId}")
    @Results({
            @Result(property = "hiId", column = "hi_id"),
            @Result(property = "tiSwId", column = "ti_sw_id"),
            @Result(property = "tiCode", column = "ti_code"),
            @Result(property = "tiType", column = "ti_type"),
            @Result(property = "tiUpTime", column = "ti_up_time"),
            @Result(property = "tiDownTime", column = "ti_down_time"),
            @Result(property = "tiUserId", column = "ti_user_id"),
            @Result(property = "tiUserName", column = "ti_user_name"),
            @Result(property = "emMode", column = "em_mode"),
            @Result(property = "emStatus", column = "em_status"),

            @Result(property = "mId", column = "m_id"),
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),

    })
    Terminal getTerminal(@Param("tiId") String tiId);

    /**
     * 根据酒店ID、设备状态 查询终端列表
     *
     * @param hiId 酒店ID
     * @param emStatus 终端状态
     * @param num 页码数
     * @param limit 每页列表大小
     * @return List
     */
    @SelectProvider(type = TerminalMapper.class, method = "getList")
    @Results({
            @Result(property = "hiId", column = "hi_id"),
            @Result(property = "tiSwId", column = "ti_sw_id"),
            @Result(property = "tiCode", column = "ti_code"),
            @Result(property = "tiType", column = "ti_type"),
            @Result(property = "tiUpTime", column = "ti_up_time"),
            @Result(property = "tiDownTime", column = "ti_down_time"),
            @Result(property = "tiUserId", column = "ti_user_id"),
            @Result(property = "tiUserName", column = "ti_user_name"),
            @Result(property = "emMode", column = "em_mode"),
            @Result(property = "emStatus", column = "em_status"),

            @Result(property = "mId", column = "m_id"),
            @Result(property = "tiId", column = "ti_id"),
            @Result(property = "cUserId", column = "c_user_id"),
            @Result(property = "cUserName", column = "c_user_name"),
            @Result(property = "mUserId", column = "m_user_id"),
            @Result(property = "mUserName", column = "m_user_name"),
            @Result(property = "cTime", column = "c_time"),
            @Result(property = "mTime", column = "m_time"),
            @Result(property = "delFlag", column = "del_flag"),

    })
    List<Terminal> getList(@Param("hiId") String hiId, @Param("emStatus") String emStatus, @Param("num") int num, @Param("limit") int limit);

    /**
     * 获取终端信息Count
     *
     * @param hiId 酒店ID
     * @param emStatus 终端状态
     * @return int
     */
    @SelectProvider(type = TerminalMapper.class, method = "getListCount")
    int getListCount(@Param("hiId") String hiId, @Param("emStatus") String emStatus);

    /**
     * 获取系统所有没有删除的终端ID
     *
     * @return List
     */
    @Select("SELECT ti_id FROM cf_ti_terminal WHERE del_flag !=1")
    @Results({
            @Result(property = "tiId", column = "ti_id"),
    })
    List<String> getTerminalId();
}
