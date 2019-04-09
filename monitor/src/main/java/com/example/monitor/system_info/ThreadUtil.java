package com.example.monitor.system_info;

import com.example.monitor.util.FileRead;
import com.example.monitor.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程定时任务
 *
 * @author 白帅雷
 * @date 2019-01-22
 */
@Component
public class ThreadUtil implements CommandLineRunner {
    private static Logger logger = Logger.getLogger(ThreadUtil.class);
    private static Map<String, Object> map = new HashMap<>(1);
    private static ScheduledExecutorService executorService;
    private static String tiId;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SystemInfoDetector systemInfoDetector;
    @Autowired
    private PropertiesUtil propertiesUtil;
    @Autowired
    private FileRead fileRead;

    /**
     * 定时任务
     */
    private void timeTask() {

        Runnable runnable = () -> {
            try {
                tiId = fileRead.getTiId();
                map.put("tiId", tiId);
                postMonitor();

            } catch (Exception e) {
                logger.error("Err.runnable" + e);
            }
        };
        executorService = Executors
                .newSingleThreadScheduledExecutor();
        // 定时执行任务(时间间隔: 10秒)
        executorService.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);

    }

    /**
     * 获取运行资源并发送给后台
     */
    private void postMonitor() {

        if (tiId != null) {
            SystemInfo systemInfo = systemInfoDetector.detect();
            systemInfo.setTiId(tiId);
            // string
            map.put("sysCpu", systemInfo.getCpuUse());
            map.put("useCpu", systemInfo.getAppCpuUse());
            // long
            long sysMem = systemInfo.getSystemMemUse();
            map.put("sysMem", sysMem);
            long useMem = (systemInfo.getAppMemUse());
            map.put("useMem", useMem);
            // long
            long sysDisk = systemInfo.getDiskSize();
            map.put("sysDisk", sysDisk);
            long useDisk = systemInfo.getDiskUsedSize();
            map.put("useDisk", useDisk);
            // 毫秒
            map.put("time", systemInfo.getMonitorTime());
            String ip = propertiesUtil.getValue("ip");
            int port = Integer.parseInt(propertiesUtil.getValue("port"));
            String url = "http://" + ip + ":" + port + "/MonitorHistory/monitorHistory";
            ResponseEntity<Map> response;
            try {
                response = restTemplate.postForEntity(url, map, Map.class);
                int status = response.getStatusCodeValue();
                System.out.println(map + " ,url." + url + " ,status." + status);
            } catch (RestClientException e) {
                logger.error("Err.后台服务异常!" + e);
            }

        } else {
            // 从配置文件中读取终端ID
            tiId = fileRead.getTiId();
        }
    }

    @Override
    public void run(String... args) {
        timeTask();
    }
}
