package com.example.monitor.terminal_install;

import com.example.monitor.util.FileRead;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TimingTask implements CommandLineRunner {
    private static Logger logger = Logger.getLogger(TimingTask.class);
    private static ScheduledExecutorService executorService;

    @Autowired
    private FileRead fileRead;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UninstallService uninstallService;
    @Autowired
    private InstallService installService;
    @Autowired
    private FileUtil fileUtil;
    private static String tiId;
    private static String version;

    /**
     * 判断主程序是否存在（true：存在，即不是首次安装；false：不存在即首次安装）
     */
    private static boolean chinaHotelHelpIsExist = false;

    /**
     * 相隔一天的毫秒数
     */
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    private void timeTask() {
        Calendar calendar = Calendar.getInstance();
        // 凌晨两点
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // 获得当前时间毫秒数
        long initTime = calendar.getTime().getTime() + PERIOD_DAY;
        // 定时任务执行得延迟毫秒数
        long delay = initTime - System.currentTimeMillis();
        chinaHotelHelpIsExist = fileUtil.chinaHotelHelpIfExist();
        // 定时任务（访问白名单接口，根据返回结果进而判断是否进行安装升级等操作）
        Runnable runnable = () -> {
            try {
                tiId = fileRead.getTiId();
                version = fileRead.getVersion();
                getWhiteList();

            } catch (Exception e) {
                logger.error("Err.timeTask" + e);
            }
        };
        executorService = Executors
                .newSingleThreadScheduledExecutor();
        // 定时执行任务
        executorService.scheduleAtFixedRate(runnable, delay, PERIOD_DAY, TimeUnit.MILLISECONDS);
    }

    /**
     * 调用白名单接口
     * 入参：终端编号、终端版本号、是否首次安装
     * 返回：该终端编号是否在白名单内、版本号是否正确
     * (正确：目标版本号大于当前版本号、不正确：目标版本号小于等于当前版本号)、目标版本号
     */
    private void getWhiteList() {
        if (tiId == null) {
            // 终端编号为空、获取终端编号
            tiId = fileRead.getTiId();
        } else if (chinaHotelHelpIsExist){
            // 不是首次安装、获取版本号
            version = fileRead.getVersion();
        } else if (version == null) {
            // 版本号为空、获取版本号
            version = fileRead.getVersion();
        } else {
            // 调用接口
            String url = "http://139.159.140.8:8080/";
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
            if (responseEntity.getStatusCodeValue() == 200) {
                responseEntity.getBody();
                String downloadUrl = "";
                mainService(downloadUrl);
            }
        }
    }

    /**
     * 主要的安装卸载服务
     * 1、下载目标版本主程序
     * 2、停止当前所有服务
     * 3、备份当前主程序（压缩）
     * 4、卸载安装程序、exe程序
     * 5、解压压缩包
     * 6、执行安装程序
     * 7、执行安装服务
     * 8、执行启动服务
     *
     * @param downloadUrl 下载版本主程序地址
     */
    private void mainService(String downloadUrl) {
        // 下载版本主程序
        String packageName = fileUtil.downloadPackage(downloadUrl);
        if (packageName != null) {
            if (chinaHotelHelpIsExist) {
                // 存在主程序，即不是首次安装，则执行卸载服务
                uninstallService.uninstallService();
                // 安装服务
                installService.installService(packageName);
            } else {
                // 不存在主程序，即首次安装，直接安装服务
                installService.installService(packageName);
            }
        }

    }

    @Override
    public void run(String... args) {
        System.out.println("----");
//        timeTask();
    }
}
