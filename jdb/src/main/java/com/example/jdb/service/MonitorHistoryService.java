package com.example.jdb.service;

import com.example.jdb.entity.MonitoringHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 设备运行占用系统资源状况Service层
 *
 * @author 白帅雷
 * @date 2019-01-09
 */
@Service
public interface MonitorHistoryService {

    /**
     * 添加 设备运行占用资源状况
     *
     * @param map map
     * @return int
     */
    int addMonitorHistory(Map<String, Object> map);

    /**
     * 设备运行占用资源详细信息(默认当天)
     *
     * @param tiId tiId
     * @return Map
     */
    Map<String, Object> getDetailInfo(String tiId);

    /**
     * 设备运行占用资源详细信息(条件：近三天、近七天)
     *
     * @param tiId tiId
     * @return Map
     */
    Map<String, Object> getMonitorDetailInfo(int day, String tiId);

    /**
     * 终端设备运行日志信息
     *
     * @param tiId tiId
     * @param page page
     * @param size size
     * @return Map
     */
    Map<String, Object> getMonitorLogInfo(String tiId, int page, int size);

    /**
     * 设备运行占用资源运维人员详细信息
     *
     * @param name name
     * @return Map
     */
    Map<String, Object> getCoreUserDetailInfo(String name);

    /**
     * 酒店详细信息
     *
     * @param hotelName hotelName
     * @return Map
     */
    Map<String, Object> getHotelDetailInfo(String hotelName);

    /**
     * 查看 设备运行占用资源状况
     *
     * @param tiId tiId
     * @return MonitoringHistory
     */
    List<MonitoringHistory> getMonitoringHistory(String tiId);

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @param blocName 集团名称
     * @param hiName 酒店名称
     * @param tiId 终端ID
     * @param emStatus 设备状态
     * @param limit 列表显示数量
     * @param num 页码数
     * @return List
     */
    List<MonitoringHistory> getListMonitoringHistory(String blocName, String hiName, String tiId, String emStatus, int num, int limit);

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @param tiId  终端ID
     * @param emStatus 设备状态
     * @param num 页码数
     * @param limit 列表显示数量
     * @return List
     */
    List<MonitoringHistory> getListMonitoringHistoryByTiId(String tiId, String emStatus, int num, int limit);

    /**
     * count
     *
     * @param blocName 集团名称
     * @param hiName 酒店名称
     * @param tiId 终端ID
     * @param emStatus 设备状态
     * @return List
     */
    int getListMonitoringHistoryCount(String blocName, String hiName, String tiId, String emStatus);

    /**
     * count
     *
     * @param tiId  终端ID
     * @param emStatus 设备状态
     * @return List
     */
    int getListMonitoringHistoryByTiIdCount(String tiId, String emStatus);

    /**
     * 查询集团名称
     *
     * @return
     */
    Map<String, Object> getBlocName();

    /**
     * 查询酒店名称
     *
     * @param blocName 集团名称
     * @return
     */
    Map<String, Object> getHotelName(String blocName);

}
