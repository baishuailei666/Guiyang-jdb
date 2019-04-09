package com.example.jdb.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Objects;

/**
 * 酒店信息Dao层动态生成SQL
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
public class HotelMapper {
    /**
     * 根据酒店名称和集团ID 查询酒店ID
     *
     * @param hotelName 酒店名称
     * @param blocId 集团ID
     * @return string
     */
    public static String getHotelId(@Param("hotelName") String hotelName, @Param("blocId") String blocId) {
        return new SQL() {{
            SELECT("hi_id");
            FROM("cf_ti_hotel");
            if (hotelName != null & !Objects.equals(hotelName, "") && blocId != null & !Objects.equals(blocId, "")) {
                WHERE("hi_name = #{hotelName} AND bloc_id = #{blocId}");
            } else if (blocId != null & !Objects.equals(blocId, "")) {
                WHERE("bloc_id = #{blocId}");
            }
        }
        }.toString();
    }
}
