package com.example.jdb.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 批次信息Dao层
 *
 * @author 白帅雷
 * @date 2019-01-14
 */
@Service
public interface BatchDao {

    /**
     * 设备型号ID 查询批次号
     *
     * @param mId 型号ID
     * @return string
     */
    @Select("SELECT bh_id FROM cf_ti_batch WHERE m_id = #{mId}")
    String getBatchId(@Param("mId") String mId);

}
