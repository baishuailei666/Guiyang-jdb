package com.example.jdb.dao;

import com.example.jdb.dao.mapper.HotelMapper;
import com.example.jdb.entity.Hotel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 酒店信息Dao层
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
@Service
public interface HotelDao {

    /**
     * 根据酒店名称和集团ID 查询酒店ID
     *
     * @param hotelName 酒店名称
     * @param blocId 集团ID
     * @return List
     */
//    @Select("SELECT hi_id FROM cf_ti_hotel WHERE hi_name = #{hotelName} AND bloc_id = #{blocId}")
    @SelectProvider(type = HotelMapper.class, method = "getHotelId")
    List<String> getHotelId(@Param("hotelName") String hotelName, @Param("blocId") String blocId);

    /**
     * 查询酒店名称
     *
     * @return List
     */
    @Select("SELECT DISTINCT hi_name FROM cf_ti_hotel WHERE bloc_id = #{blocId} AND del_flag !=1")
    List<String> getHotelName(@Param("blocId") String blocId);

    /**
     * 酒店ID 查询酒店名称
     *
     * @param hotelId 终端ID
     * @return string
     */
    @Select("SELECT hi_name FROM cf_ti_hotel WHERE hi_id = #{hotelId}")
    String getHotelNameByHiId(@Param("hotelId") String hotelId);

    /**
     * 查询酒店详情
     *
     * @param hotelName 酒店名称
     * @return string
     */
    @Select("SELECT * FROM cf_ti_hotel WHERE hi_name = #{hotelName}")
    @Results({
            @Result(property = "hiId", column = "hi_id"),
            @Result(property = "hiName", column = "hi_name"),
            @Result(property = "hiCode", column = "hi_code"),
            @Result(property = "blocId", column = "bloc_id"),
            @Result(property = "hiLegalPerson", column = "hi_legalperson"),
            @Result(property = "hiTel", column = "hi_tel"),
            @Result(property = "hiType", column = "hi_type"),
            @Result(property = "hiAddress", column = "hi_address"),
            @Result(property = "hiDistrictsCode", column = "hi_districts_code"),
            @Result(property = "hiLongitude", column = "hi_longitude"),
            @Result(property = "hiLatitude", column = "hi_latitude"),
            @Result(property = "hiIfDemoHotel", column = "hi_ifdemohotel"),
    })
    Hotel getHotelInfo(@Param("hotelName") String hotelName);
}
