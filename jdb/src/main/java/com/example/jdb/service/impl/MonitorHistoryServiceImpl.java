package com.example.jdb.service.impl;

import com.example.jdb.dao.*;
import com.example.jdb.entity.*;
import com.example.jdb.service.MonitorHistoryService;
import com.example.jdb.utils.DateTimeUtil;
import com.example.jdb.utils.ElasticSearchUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 设备运行占用系统资源状况ServiceImpl层
 *
 * @author 白帅雷
 * @date 2019-01-09
 */
@Service
public class MonitorHistoryServiceImpl implements MonitorHistoryService {

    private static Logger logger = Logger.getLogger(MonitorHistoryServiceImpl.class);
    @Autowired
    private MonitorHistoryDao monitorHistoryDao;
    @Autowired
    private TerminalDao terminalDao;
    @Autowired
    private BlocDao blocDao;
    @Autowired
    private HotelDao hotelDao;
    @Autowired
    CoreUserDao coreUserDao;
    @Autowired
    private BatchDao batchDao;
    @Autowired
    private SoftwareInfoDao softwareInfoDao;
    private static final long M = 1024 * 1024;

    /**
     * 添加 设备运行占用资源状况
     *
     * @param map map
     * @return int
     */
    @Override
    public int addMonitorHistory(Map<String, Object> map) {
        logger.info("[终端设备运行占用资源状况] [map:" + map + "]");

        Terminal terminal = terminalDao.getTerminal(String.valueOf(map.get("tiId")));
        MonitoringHistory monitoringHistory = new MonitoringHistory();
        try {
            // 百分比
            monitoringHistory.setSysCpu(BigDecimal.valueOf((Double) map.get("sysCpu") * 100));
            monitoringHistory.setUseCpu(BigDecimal.valueOf((Double) map.get("useCpu") * 100));
            // 单位M
            monitoringHistory.setSysMem(BigDecimal.valueOf(Long.valueOf(String.valueOf(map.get("sysMem"))) / M));
            monitoringHistory.setUseMem(BigDecimal.valueOf(Long.valueOf(String.valueOf(map.get("useMem"))) / M));
            // 单位G
            monitoringHistory.setSysDisk(BigDecimal.valueOf(Long.valueOf(String.valueOf(map.get("sysDisk"))) / M));
            monitoringHistory.setUseDisk(BigDecimal.valueOf(Long.valueOf(String.valueOf( map.get("useDisk"))) / M));
            monitoringHistory.setMonitorTime(DateTimeUtil.getFormatTime(Long.valueOf(String.valueOf(map.get("time")))));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            logger.error("Err.终端设备运行资源数据类型转换异常!" + e);
        }

        if (terminal != null) {
            monitoringHistory.setTiId(terminal.getTiId());
            monitoringHistory.setcTime(terminal.getcTime());
            monitoringHistory.setcUserId(terminal.getcUserId());
            monitoringHistory.setcUserName(terminal.getcUserName());
            monitoringHistory.setDelFlag(terminal.getDelFlag());
            monitoringHistory.setmTime(terminal.getmTime());
            monitoringHistory.setmUserId(terminal.getmUserId());
            monitoringHistory.setmUserName(terminal.getmUserName());
            return monitorHistoryDao.addMonitorHistory(monitoringHistory);

        } else {
            logger.error("Err.tiId不存在." + map.get("tiId"));
            return 0;
        }

    }

    /**
     * 设备运行占用资源详细信息(默认当天)
     *
     * @param tiId 终端ID
     * @return Map
     */
    @Override
    public Map<String, Object> getDetailInfo(String tiId) {
        Map<String, Object> map = new HashMap<>(1);
        List<MonitoringHistory> list = monitorHistoryDao.getMonitoringHistory(tiId);

        Map<String, Object> listDiskMap = new HashMap<>(1);
        Map<String, Object> listCpuMap = new HashMap<>(1);
        Map<String, Object> listMemMap = new HashMap<>(1);

        List<Object> listSysCpu = new ArrayList<>(1);
        List<Object> listUseCpu = new ArrayList<>(1);

        List<Object> listSysMem = new ArrayList<>(1);
        List<Object> listUseMem = new ArrayList<>(1);

        List<Object> listSysDisk = new ArrayList<>(1);
        List<Object> listUseDisk = new ArrayList<>(1);
        List<Object> time = new ArrayList<>(1);

        Terminal terminal = terminalDao.getTerminal(tiId);
        terminal.setTiUpTime(terminal.getTiUpTime().substring(0, terminal.getTiUpTime().length()-2));
        map.put("terminal", terminal);
        map.put("tiId", tiId);
        SoftwareInfo softwareInfo = softwareInfoDao.getSoftwareInfo(terminal.getTiSwId());
        map.put("softwareInfo", softwareInfo);
        map.put("hotelName", hotelDao.getHotelNameByHiId(terminal.getHiId()));
        map.put("bhId", batchDao.getBatchId(terminal.getmId()));
        map.put("mId", terminal.getmId());
        if (list.size() != 0) {
            // 运行资源
            list.forEach(entity -> {
                entity.setMonitorTime(entity.getMonitorTime().substring(0, entity.getMonitorTime().length()-2));

                listSysCpu.add(entity.getSysCpu());
                listUseCpu.add(entity.getUseCpu());
                time.add(entity.getMonitorTime());
                listCpuMap.put("sysCpu", listSysCpu);
                listCpuMap.put("useCpu", listUseCpu);
                listCpuMap.put("time", time);


                listSysMem.add(entity.getSysMem());
                listUseMem.add(entity.getUseMem());
                listMemMap.put("sysMem", listSysMem);
                listMemMap.put("useMem", listUseMem);
                listMemMap.put("time", time);

                listSysDisk.add(entity.getSysDisk());
                listUseDisk.add(entity.getUseDisk());
                listDiskMap.put("sysDisk", listSysDisk);
                listDiskMap.put("useDisk", listUseDisk);
                listDiskMap.put("time", time);
            });
            map.put("code", 0);
            map.put("msg", "成功");
        } else {
            map.put("code", 1);
            map.put("msg", "失败");
        }
        map.put("cpu", listCpuMap);
        map.put("mem", listMemMap);
        map.put("disk", listDiskMap);
        return map;

    }

    /**
     * 设备运行占用资源详细信息(条件：近三天、近七天)
     *
     * @param day 查询天数
     * @param tiId 终端ID
     * @return Map
     */
    @Override
    public Map<String, Object> getMonitorDetailInfo(int day, String tiId) {
        Map<String, Object> map = new HashMap<>(1);
        // 查询近三天day=3、近七天day=7
        List<MonitoringHistory> monitoringHistoryList = monitorHistoryDao.getMonitoringHistoryDay(day-1, tiId);

        List<Object> listDaySysDisk = new ArrayList<>(1);
        List<Object> listDayUseDisk = new ArrayList<>(1);
        List<Object> time = new ArrayList<>(1);

        List<Object> listDaySysMem = new ArrayList<>(1);
        List<Object> listDayUseMem = new ArrayList<>(1);

        List<Object> listDaySysCpu = new ArrayList<>(1);
        List<Object> listDayUseCpu = new ArrayList<>(1);


        Map<String, Object> listDayDiskMap = new HashMap<>(1);
        Map<String, Object> listDayCpuMap = new HashMap<>(1);
        Map<String, Object> listDayMemMap = new HashMap<>(1);

        if (monitoringHistoryList.size() != 0) {
            map.put("code", 0);
            map.put("msg", "成功");
            // 运行资源
            monitoringHistoryList.forEach(monitoringHistory -> {
                monitoringHistory.setMonitorTime(monitoringHistory.getMonitorTime().substring(0, monitoringHistory.getMonitorTime().length()-2));

                listDaySysCpu.add(monitoringHistory.getSysCpu());
                listDayUseCpu.add(monitoringHistory.getUseCpu());
                time.add(monitoringHistory.getMonitorTime());
                listDayCpuMap.put("sysCpu", listDaySysCpu);
                listDayCpuMap.put("useCpu", listDayUseCpu);
                listDayCpuMap.put("time", time);

                listDaySysMem.add(monitoringHistory.getSysMem());
                listDayUseMem.add(monitoringHistory.getUseMem());
                listDayMemMap.put("sysMem", listDaySysMem);
                listDayMemMap.put("useMem", listDayUseMem);
                listDayMemMap.put("time", time);

                listDaySysDisk.add(monitoringHistory.getSysDisk());
                listDayUseDisk.add(monitoringHistory.getUseDisk());
                listDayDiskMap.put("sysDisk", listDaySysDisk);
                listDayDiskMap.put("useDisk", listDayUseDisk);
                listDayDiskMap.put("time", time);

            });
            map.put("cpu", listDayCpuMap);
            map.put("mem", listDayMemMap);
            map.put("disk", listDayDiskMap);
            map.put("day", day);
        } else {
            map.put("code", 1);
            map.put("msg", "失败");
        }
        return map;
    }

    /**
     * 终端设备运行日志信息
     *
     * @param tiId 终端ID
     * @param page 页码数
     * @param size 每页列表的大小
     * @return Map
     */
    @Override
    public Map<String, Object> getMonitorLogInfo(String tiId, int page, int size) {
        // 终端日志
        return ElasticSearchUtil.queryLogFromEs(tiId, page, size);
    }

    /**
     * 设备运行占用资源运维人员详细信息
     *
     * @param name 运维人员名称
     * @return Map
     */
    @Override
    public Map<String, Object> getCoreUserDetailInfo(String name) {
        Map<String, Object> map = new HashMap<>(1);
        CoreUser coreUser = coreUserDao.getCoreUser(name);
        if (coreUser != null) {
            coreUser.setCreateTime(coreUser.getCreateTime().substring(0, coreUser.getCreateTime().length() - 2));
            coreUser.setUpdateTime(coreUser.getUpdateTime().substring(0, coreUser.getUpdateTime().length() - 2));
            map.put("code", 0);
            map.put("msg", "成功");
            map.put("coreUserInfo", coreUser);
        } else {
            map.put("code", 1);
            map.put("msg", "失败");
        }

        return map;
    }

    /**
     * 酒店详细信息
     *
     * @param hotelName 酒店名称
     * @return Map
     */
    @Override
    public Map<String, Object> getHotelDetailInfo(String hotelName) {
        Map<String, Object> map = new HashMap<>(1);

        Hotel hotel =  hotelDao.getHotelInfo(hotelName);
        if (hotel.getBlocId() == null) {
            map.put("code", 1);
            map.put("msg", "失败");
            map.put("data","hotel.getBlocId is null!");
        } else {
            map.put("code", 0);
            map.put("msg", "成功");
            String blocName = blocDao.getBlocNameById(hotel.getBlocId());
            hotel.setBlocName(blocName);
            map.put("data", hotel);
        }
        return map;
    }

    /**
     * 查看 设备运行占用资源状况
     *
     * @param tiId 终端ID
     * @return MonitoringHistory
     */
    @Override
    public List<MonitoringHistory> getMonitoringHistory(String tiId) {
        logger.info("[查询设备运行占用资源状况] tiId." + tiId);
        List<MonitoringHistory> list = monitorHistoryDao.getMonitoringHistory(tiId);
        if (list.size() != 0) {
            list.forEach(entity -> {
                // 监控时间
                int lenMonitorTime = entity.getMonitorTime().length();
                entity.setMonitorTime(entity.getMonitorTime().substring(0, lenMonitorTime-2));
                if (entity.getcTime() != null) {
                    // 创建时间
                    int lenCtime = entity.getcTime().length();
                    entity.setcTime(entity.getcTime().substring(0, lenCtime-2));
                }

                if (entity.getmTime() != null) {
                    // 修改时间
                    int lenMtime = entity.getmTime().length();
                    entity.setmTime(entity.getmTime().substring(0, lenMtime-2));
                }
            });
        }

        return list;
    }

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @param blocName 集团名称
     * @param hiName 酒店名称
     * @param tiId 终端ID
     * @param emStatus 设备状态
     * @param num      页码数
     * @param limit 列表显示数量
     * @return List
     */
    @Override
    public List<MonitoringHistory> getListMonitoringHistory(String blocName, String hiName, String tiId, String emStatus, int num, int limit) {
        logger.info("[分页查询所有设备运行占用资源状况]");
        logger.info("索引条件:blocName." + blocName + " ,hiName." + hiName + " ,tiId." + tiId + " ,num." + num + " ,limit." + limit);
        List<MonitoringHistory> lists = new ArrayList<>(1);
        // 得到集团ID
        List<String> blocIdList = blocDao.getBlocId(blocName);
        logger.info("blocIdList." + blocIdList);
        blocIdList.forEach(blocId -> {
            // 得到酒店ID
            List<String> hiIdList = hotelDao.getHotelId(hiName, blocId);
            logger.info("hiIdList." + hiIdList);
            hiIdList.forEach(hiId -> {
                // 得到终端信息
                List<Terminal> terminalList = terminalDao.getList(hiId, emStatus, num, limit);

                terminalList.forEach(terminal -> {

                    MonitoringHistory monitoringHistory = new MonitoringHistory();
                    monitoringHistory.setTiId(terminal.getTiId());
                    monitoringHistory.setHiId(terminal.getHiId());
                    monitoringHistory.setTiSwId(terminal.getTiSwId());
                    monitoringHistory.setTiCode(terminal.getTiCode());
                    monitoringHistory.setEmStatus(terminal.getEmStatus());
                    monitoringHistory.setTiUserName(terminal.getTiUserName());
                    monitoringHistory.setHotelName(hotelDao.getHotelNameByHiId(terminal.getHiId()));
                    monitoringHistory.setBhId(batchDao.getBatchId(terminal.getmId()));
                    monitoringHistory.setmId(terminal.getmId());
                    lists.add(monitoringHistory);

                });
            });
        });

        return lists;
    }

    /**
     * 查看 设备运行占用资源状况(分页)
     *
     * @param tiId     终端ID
     * @param emStatus 设备状态
     * @param num      页码数
     * @param limit 列表显示数量
     * @return List
     */
    @Override
    public List<MonitoringHistory> getListMonitoringHistoryByTiId(String tiId, String emStatus, int num, int limit) {

        List<MonitoringHistory> list = monitorHistoryDao.getListMonitoringHistoryByTiId(tiId, emStatus, num, limit);
        list.forEach(monitoring -> {
            Terminal terminal = terminalDao.getTerminal(monitoring.getTiId());
            // 酒店名称
            monitoring.setHotelName(hotelDao.getHotelNameByHiId(terminal.getHiId()));
            // 批次编号
            monitoring.setBhId(batchDao.getBatchId(terminal.getmId()));
            // 设备型号
            monitoring.setmId(terminal.getmId());

            int lenMonitorTime = monitoring.getMonitorTime().length();
            monitoring.setMonitorTime(monitoring.getMonitorTime().substring(0, lenMonitorTime - 2));

            if (monitoring.getcTime() != null) {
                int lenCTime = monitoring.getcTime().length();
                monitoring.setcTime(monitoring.getcTime().substring(0, lenCTime - 2));
            } else {
                monitoring.setcTime(monitoring.getcTime());
            }

            if (monitoring.getmTime() != null) {
                int lenMTime = monitoring.getmTime().length();
                monitoring.setmTime(monitoring.getmTime().substring(0, lenMTime - 2));
            }
        });

        return list;
    }

    /**
     * count
     *
     * @param blocName 集团名称
     * @param hiName   酒店名称
     * @param tiId     终端ID
     * @param emStatus 设备状态
     * @return List
     */
    @Override
    public int getListMonitoringHistoryCount(String blocName, String hiName, String tiId, String emStatus) {
        AtomicInteger result = new AtomicInteger();
        List<String> blocIdList = blocDao.getBlocId(blocName);

        blocIdList.forEach(blocId -> {
            // 得到酒店ID
            List<String> hiIdList = hotelDao.getHotelId(hiName, blocId);

            hiIdList.forEach(hiId -> {
                // 获取终端count数
                int count = terminalDao.getListCount(hiId, emStatus);
                result.addAndGet(count);
            });
        });

        return result.get();
    }

    /**
     * count
     *
     * @param tiId     终端ID
     * @param emStatus 设备状态
     * @return List
     */
    @Override
    public int getListMonitoringHistoryByTiIdCount(String tiId, String emStatus) {
        return monitorHistoryDao.getListMonitoringHistoryByTiIdCount(tiId, emStatus);
    }

    /**
     * 查询集团名称
     *
     * @return Map
     */
    @Override
    public Map<String, Object> getBlocName() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("blocNames", blocDao.getBlocName());
        return map;
    }

    /**
     * 查询酒店名称
     *
     * @param blocName 集团名称
     * @return Map
     */
    @Override
    public Map<String, Object> getHotelName(String blocName) {
        Map<String, Object> map = new HashMap<>(1);
        List<String> blocIdList = blocDao.getBlocId(blocName);
        blocIdList.forEach(blocId -> {
            map.put("hotelNames", hotelDao.getHotelName(blocId));
        });
        map.put("code", 0);
        map.put("msg", "成功");
        return map;
    }
}
