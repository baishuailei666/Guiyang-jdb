package com.example.jdb.web;


import com.example.jdb.entity.MonitoringHistory;
import com.example.jdb.service.MonitorHistoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 设备运行占用系统资源状况Web层
 *
 * @author 白帅雷
 * @date 2019-01-09
 */
@RestController
@CrossOrigin
public class MonitorHistoryController {

    private static Logger logger = Logger.getLogger(MonitorHistoryController.class);
    @Autowired
    private MonitorHistoryService monitorHistoryService;

    @GetMapping(value = "")
    public String getIndex() {
        return "Hello, This is Monitor!";
    }

    /**
     * 添加 设备运行占用资源状况
     *
     * @param map map
     */
    @ApiOperation(value = "添加设备运行占用资源状况")
    @ApiImplicitParam(name ="map", value = "JSON格式数据", dataType = "Map")
    @RequestMapping(value = "/monitorHistory", method = RequestMethod.POST)
    @ResponseBody
    public void addMonitorHistory(@RequestBody Map<String, Object> map) {
        logger.info("THIS IS A INTERFACE ADD MONITOR HISTORY!");
        this.monitorHistoryService.addMonitorHistory(map);
    }

    /**
     * 设备运行占用资源详细信息(默认当天)
     *
     * @param tiId 终端ID
     * @return Map
     */
    @ApiOperation(value = "设备运行占用资源详细信息(默认当天)")
    @ApiImplicitParam(name ="tiId", value = "终端ID", dataType = "String")
    @GetMapping(value = "/monitorHistory/info")
    public Map<String, Object> getDetailInfo(@RequestParam("tiId") String tiId) {
        logger.info("THIS IS GET DETAIL INFO MONITOR HISTORY! [tiId:" + tiId + "]");
        return this.monitorHistoryService.getDetailInfo(tiId);
    }

    /**
     * 设备运行占用资源详细信息(条件：近三天、近七天)
     *
     * @param tiId 终端ID
     * @param day 查询天数
     * @return Map
     */
    @ApiOperation(value = "设备运行占用资源详细信息(条件：近三天、近七天)")
    @ApiImplicitParam(name ="tiId", value = "终端ID", dataType = "String")
    @GetMapping(value = "/monitorHistory/dayInfo")
    public Map<String, Object> getMonitorDetailInfo(@RequestParam("tiId") String tiId, @RequestParam("day") int day) {
        logger.info("THIS IS GET DETAIL INFO MONITOR HISTORY! [tiId:" + tiId + ", day: " + day + "]");
        return this.monitorHistoryService.getMonitorDetailInfo(day, tiId);
    }

    /**
     * 终端设备运行日志信息
     *
     * @param tiId 终端ID
     * @param page 页码数
     * @param size 每页列表大小
     * @return Map
     */
    @ApiOperation(value = "终端设备运行日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="tiId", value = "终端ID", dataType = "String"),
            @ApiImplicitParam(name ="page", value = "页码", dataType = "int"),
            @ApiImplicitParam(name ="size", value = "数量", dataType = "int")
    })
    @GetMapping(value = "/monitorHistory/log")
    public Map<String, Object> getMonitorLogInfo(@RequestParam("tiId") String tiId
            , @RequestParam("page") int page, @RequestParam("size") int size) {
        logger.info("THIS IS GET DETAIL INFO MONITOR HISTORY! [tiId: " + tiId + " ,page:" + page + " ,size: " + size + "]");
        return this.monitorHistoryService.getMonitorLogInfo(tiId, page, size);
    }

    /**
     * 查看 设备运行占用资源状况
     *
     * @param tiId 终端ID
     * @return Map
     */
    @GetMapping(value = "/monitorHistory/{tiId}")
    public Map<String, Object> getMonitorHistory(@PathVariable String tiId) {
        logger.info("THIS IS GET MONITOR HISTORY!");
        Map<String, Object> map = new HashMap<>(1);
        List<MonitoringHistory> monitoringHistory = this.monitorHistoryService.getMonitoringHistory(tiId);
        map.put("monitorHistory", monitoringHistory);
        return map;
    }

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @param blocName 集团名称
     * @param hiName 酒店名称
     * @param tiId 终端ID
     * @param emStatus 设备状态
     * @return Map
     */
    @ApiOperation(value = "设备运行占用资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码数", dataType = "int"),
            @ApiImplicitParam(name = "blocName", value = "酒店集团名称", dataType = "String"),
            @ApiImplicitParam(name = "hiName", value = "酒店名称", dataType = "String"),
            @ApiImplicitParam(name = "tiId", value = "终端ID", dataType = "String"),
            @ApiImplicitParam(name = "emStatus", value = "设备状态", dataType = "String"),
            @ApiImplicitParam(name = "limit", value = "列表显示数量", dataType = "int")
    })
    @GetMapping(value = "/monitorHistory/list")
    public Map<String, Object> getListMonitorHistory(@RequestParam("blocName") String blocName
            , @RequestParam("hiName") String hiName ,@RequestParam("tiId") String tiId
            , @RequestParam("emStatus") String emStatus,@RequestParam("num") int num,  @RequestParam("limit") int limit) {
        logger.info("THIS IS GET MONITOR HISTORY LIST!");
        Map<String, Object> map = new LinkedHashMap<>(1);

        if (Objects.equals(blocName, "")){
            List<MonitoringHistory> list = this.monitorHistoryService.getListMonitoringHistoryByTiId(tiId, emStatus, (num - 1) * limit, limit);
            map.put("code", 0);
            map.put("msg", "成功");
            map.put("data", list);
            return map;
        } else {
            List<MonitoringHistory> list = this.monitorHistoryService.getListMonitoringHistory(blocName, hiName, tiId, emStatus, (num - 1) * limit, limit);
            map.put("code", 0);
            map.put("msg", "成功");
            map.put("data", list);
            return map;
        }
    }

    /**
     * count
     *
     * @param blocName 集团名称
     * @param hiName 酒店名称
     * @param tiId 终端ID
     * @param emStatus 设备状态
     * @return Map
     */
    @ApiOperation(value = "设备运行占用资源COUNT数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blocName", value = "酒店集团名称", dataType = "String"),
            @ApiImplicitParam(name = "hiName", value = "酒店名称", dataType = "String"),
            @ApiImplicitParam(name = "tiId", value = "终端ID", dataType = "String"),
            @ApiImplicitParam(name = "emStatus", value = "设备状态", dataType = "String")
    })
    @GetMapping(value = "/monitorHistory/count")
    public Map<String, Object> getListMonitorHistoryCount(@RequestParam("blocName") String blocName
            ,@RequestParam("hiName") String hiName ,@RequestParam("tiId") String tiId , @RequestParam("emStatus") String emStatus) {
        logger.info("THIS IS GET MONITOR HISTORY COUNT!");
        Map<String, Object> map = new LinkedHashMap<>(1);

        if (Objects.equals(blocName, "")){
            int count = this.monitorHistoryService.getListMonitoringHistoryByTiIdCount(tiId, emStatus);
            map.put("code", 0);
            map.put("msg", "成功");
            map.put("data", count);
            return map;
        } else {
            int count = this.monitorHistoryService.getListMonitoringHistoryCount(blocName, hiName, tiId, emStatus);
            map.put("code", 0);
            map.put("msg", "成功");
            map.put("data", count);
            return map;
        }
    }

    /**
     * 设备运行占用资源运维人员详细信息
     *
     * @param name 运维人员名称
     * @return Map
     */
    @ApiOperation(value = "设备运行占用资源运维人员详细信息")
    @ApiImplicitParam(name ="name", value = "运维人员名称", dataType = "String")
    @GetMapping(value = "/monitorHistory/coreUserInfo")
    public Map<String, Object> getCoreUserDetailInfo(@RequestParam("name") String name) {
        logger.info("THIS IS GET CORE USER DETAIL INFO! [name:" + name + "]");
        return this.monitorHistoryService.getCoreUserDetailInfo(name);
    }

    /**
     * 酒店详细信息
     *
     * @param hotelName 酒店名称
     * @return Map
     */
    @ApiOperation(value = "酒店详细信息")
    @ApiImplicitParam(name ="hotelName", value = "酒店名称", dataType = "String")
    @GetMapping(value = "/monitorHistory/hotelInfo")
    public Map<String, Object> getHotelDetailInfo(@RequestParam("hotelName") String hotelName) {
        logger.info("THIS IS GET HOTEL DETAIL INFO! [hotelName:" + hotelName + "]");
        return this.monitorHistoryService.getHotelDetailInfo(hotelName);
    }

    /**
     * 查询集团名称
     *
     * @return Map
     */
    @ApiOperation(value = "查询集团名称")
    @GetMapping(value = "/monitorHistory/blocName")
    public Map<String, Object> getBlocName() {
        logger.info("THIS IS GET BLOC NAME INFO!");
        return this.monitorHistoryService.getBlocName();
    }


    /**
     * 查询酒店名称
     *
     * @param blocName 集团名称
     * @return Map
     */
    @ApiOperation(value = "查询酒店名称")
    @ApiImplicitParam(name ="blocName", value = "集团名称", dataType = "String")
    @GetMapping(value = "/monitorHistory/hotelName")
    public Map<String, Object> getHotelName(@RequestParam("blocName") String blocName) {
        logger.info("THIS IS GET HOTEL NAME INFO! [blocName:" + blocName + "]");
        return this.monitorHistoryService.getHotelName(blocName);
    }
}
