package com.example.jdb.dao.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 集团信息Dao动态生成SQL
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
public class BlocMapper {

    /**
     * 根据集团名称查询集团ID
     *
     * @param blocName 集团名称
     * @return string
     */
    public static String getBlocId(@Param("blocName") String blocName) {
        return new SQL() {{
            SELECT("bloc_id");
            FROM("cf_ti_bloc");
            if (blocName != null) {
                WHERE("bloc_name = #{blocName}");
            }
        }
        }.toString();
    }
}
