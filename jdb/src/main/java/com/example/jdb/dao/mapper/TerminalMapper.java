package com.example.jdb.dao.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Objects;

/**
 * 终端Dao动态生成SQL
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
public class TerminalMapper {

    /**
     * 根据酒店ID、设备状态 查询终端列表
     *
     * @param hiId 酒店ID
     * @param emStatus 终端状态
     * @return string
     */
     public static String getList(@Param("hiId") String hiId, @Param("emStatus") String emStatus, @Param("num") int num, @Param("limit") int limit) {
         String SQL = "SELECT * FROM cf_ti_terminal WHERE del_flag !=1 ";
         if (hiId != null & !Objects.equals(hiId, "") && emStatus != null & !Objects.equals(emStatus, "")) {
             SQL += "AND hi_id = #{hiId} AND em_status = #{emStatus} LIMIT " + num + " , " + limit;

         } else if (hiId != null & !Objects.equals(hiId, "")) {
             SQL += "AND hi_id = #{hiId} LIMIT " + num + " , " + limit;

         } else if (emStatus != null & !Objects.equals(emStatus, "")) {
             SQL += "AND em_status = #{emStatus} LIMIT " + num + " , " + limit;

         } else {
             SQL += "LIMIT " + num + " , " + limit;

         }
         return SQL;
    }

    /**
     * 获取终端信息Count
     *
     * @param hiId 酒店ID
     * @param emStatus 终端状态
     * @return string
     */
    public static String getListCount(@Param("hiId") String hiId, @Param("emStatus") String emStatus) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("cf_ti_terminal");

            if (hiId != null & !Objects.equals(hiId, "") && emStatus != null & !Objects.equals(emStatus, "")) {
                WHERE("hi_id = #{hiId} AND em_status = #{emStatus} AND del_flag != 1 ");
            } else if (hiId != null & !Objects.equals(hiId, "")) {
                WHERE("hi_id = #{hiId} AND del_flag != 1 ");
            } else if (emStatus != null & !Objects.equals(emStatus, "")) {
                WHERE("em_status = #{emStatus} AND del_flag != 1 ");
            }
        }
        }.toString();
    }
}
