package com.example.jdb.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Objects;

public class MonitorHistoryMapper {

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @return List
     */
    public static String getListMonitoringHistory(@Param("tiId") String tiId, @Param("num") int num, @Param("limit") int limit) {
        String SQL = "SELECT * FROM cf_ti_terminal WHERE cf_ti_terminal.del_flag !=1 ";
        if (tiId != null & !Objects.equals(tiId, "")) {
            SQL += "AND cf_ti_terminal.ti_id = #{tiId} LIMIT " + num + ", " + limit;
        } else {
            SQL += "AND cf_ti_terminal.ti_id = #{tiId} LIMIT " + num + ", " + limit;

        }
        System.out.println(SQL);
        return SQL;
    }

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @return List
     */
    public static String getListMonitoringHistoryByTiId(@Param("tiId") String tiId, @Param("emStatus") String emStatus, @Param("num") int num, @Param("limit") int limit) {
        String SQL = "SELECT * FROM cf_ti_terminal WHERE cf_ti_terminal.del_flag !=1 ";
        if (tiId != null & !Objects.equals(tiId, "") && emStatus != null & !Objects.equals(emStatus, "")) {
            SQL += "AND cf_ti_terminal.ti_id = #{tiId} AND cf_ti_terminal.em_status = #{emStatus}" +
                    "LIMIT " + num + ", " + limit;

        } else if (tiId != null & !Objects.equals(tiId, "")) {
            SQL += "AND cf_ti_terminal.ti_id = #{tiId} LIMIT " + num + ", " + limit;

        } else if (emStatus != null & !Objects.equals(emStatus, "")) {
            SQL += "AND cf_ti_terminal.em_status = #{emStatus} LIMIT " + num + ", " + limit;

        } else {
            SQL += "LIMIT " + num + ", " + limit;

        }

        return SQL;
    }

    /**
     * count
     *
     * @return List
     */
    public static String getListMonitoringHistoryByTiIdCount(@Param("tiId") String tiId, @Param("emStatus") String emStatus) {
        String SQL = "SELECT COUNT(*) FROM cf_ti_terminal WHERE cf_ti_terminal.del_flag !=1 ";
        if (tiId != null & !Objects.equals(tiId, "") && emStatus != null & !Objects.equals(emStatus, "")) {
            SQL += "AND cf_ti_terminal.ti_id = #{tiId} AND cf_ti_terminal.em_status = #{emStatus}";

        } else if (tiId != null & !Objects.equals(tiId, "")) {
            SQL += "AND cf_ti_terminal.ti_id = #{tiId}";

        } else if (emStatus != null & !Objects.equals(emStatus, "")) {
            SQL += "AND cf_ti_terminal.em_status = #{emStatus}";

        }
        return SQL;
    }
}
