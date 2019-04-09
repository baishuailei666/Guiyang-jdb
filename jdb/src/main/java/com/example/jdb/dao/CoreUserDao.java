package com.example.jdb.dao;

import com.example.jdb.entity.CoreUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 运维人员Dao层
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
@Service
public interface CoreUserDao {

    /**
     * 运维人员详细信息
     *
     * @param name 运维人员名称
     * @return string
     */
    @Select("SELECT * FROM core_user WHERE NAME = #{name}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "code", column = "CODE"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "createTime", column = "CREATE_TIME"),
            @Result(property = "orgId", column = "ORG_ID"),
            @Result(property = "state", column = "STATE"),
            @Result(property = "jobType1", column = "JOB_TYPE1"),
            @Result(property = "delFlag", column = "DEL_FLAG"),
            @Result(property = "updateTime", column = "update_Time"),
            @Result(property = "jobType0", column = "JOB_TYPE0"),
            @Result(property = "attachmentId", column = "attachment_id"),
            @Result(property = "email", column = "email"),
            @Result(property = "phone", column = "phone"),

    })
    CoreUser getCoreUser(@Param("name") String name);
}
