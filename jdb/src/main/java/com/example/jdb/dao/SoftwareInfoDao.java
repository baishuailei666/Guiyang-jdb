package com.example.jdb.dao;

import com.example.jdb.entity.SoftwareInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 终端软件信息Dao层
 *
 * @author 白帅雷
 * @date 2019-01-14
 */
@Service
public interface SoftwareInfoDao {

    /**
     * 终端软件ID 查询终端软件
     *
     * @param tiSwId 终端软件ID
     * @return SoftwareInfo
     */
    @Select("SELECT * FROM cf_ti_softwareinfo WHERE ti_sw_id = #{tiSwId}")
    @Results({
            @Result(property = "tiSwId", column = "ti_sw_id"),
            @Result(property = "hiId", column = "hi_id"),
            @Result(property = "tiSwName", column = "ti_sw_name"),
            @Result(property = "tiSwPath", column = "ti_sw_path"),
            @Result(property = "tiSwVersion", column = "ti_sw_version"),

    })
    SoftwareInfo getSoftwareInfo(@Param("tiSwId") String tiSwId);
}
