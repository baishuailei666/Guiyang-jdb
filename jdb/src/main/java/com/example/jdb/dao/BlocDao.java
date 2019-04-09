package com.example.jdb.dao;

import com.example.jdb.dao.mapper.BlocMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 集团信息Dao层
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
@Service
public interface BlocDao {

    /**
     * 根据集团名称查询集团ID
     *
     * @param blocName 集团名称
     * @return string
     */
//    @Select("SELECT bloc_id FROM cf_ti_bloc WHERE bloc_name = #{blocName}")
    @SelectProvider(type = BlocMapper.class, method = "getBlocId")
    List<String> getBlocId(@Param("blocName") String blocName);

    /**
     * 查询集团名称
     *
     * @return string
     */
    @Select("SELECT DISTINCT bloc_name FROM cf_ti_bloc WHERE del_flag !=1")
    List<String> getBlocName();

    /**
     * 集团ID 查询集团名称
     *
     * @return string
     */
    @Select("SELECT bloc_name FROM cf_ti_bloc WHERE bloc_id = #{blocId}")
    String getBlocNameById(@Param("blocId") String blocId);
}
