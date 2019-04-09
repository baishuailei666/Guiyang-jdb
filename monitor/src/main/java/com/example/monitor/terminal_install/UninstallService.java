package com.example.monitor.terminal_install;

import com.example.monitor.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 卸载服务（停止服务、主程序备份、执行卸载脚本、执行卸载exe脚本）
 *
 * @author 白帅雷
 * @date 2019-01-29
 */
@Component
public class UninstallService {

    @Autowired
    private PropertiesUtil propertiesUtil;
    @Autowired
    private ExecuteUtil executeUtil;
    @Autowired
    private FileUtil fileUtil;
    private static Logger logger = Logger.getLogger(UninstallService.class);

    /**
     * 获得卸载路径
     *
     * @return String
     */
    private String getUninstallPath() {
        return propertiesUtil.getValue("uninstallPath");
    }

    /**
     * 卸载服务（停止服务、主程序备份、执行卸载脚本、执行卸载exe脚本）
     */
    void uninstallService() {
        // 停止服务
        serviceAllStop();
        // 主程序备份
        boolean zipChinaHotelHelp = fileUtil.zipChinaHotelHelp();
        if (zipChinaHotelHelp) {
            // 执行卸载脚本
            serviceAllUninstall();
            // 执行卸载exe脚本
            exeAllUninstall();
        }

    }

    /**
     * 停止所有服务(脚本路径：C:\\Program Files (x86)\\ChinaHotelHelp\\postinstall\\services_all_stop.bat)
     */
    private void serviceAllStop() {
        String serviceAllStop = getUninstallPath() + "services_all_stop.bat";
        logger.info("[Uninstall Service] serviceAllStop()." + serviceAllStop);
        executeUtil.callCmd(serviceAllStop);
    }

    /**
     * 卸载所有服务(脚本路径：C:\\Program Files (x86)\\ChinaHotelHelp\\postinstall\\service_all_uninstall.bat)
     */
    private void serviceAllUninstall() {
        String serviceAllUninstall = getUninstallPath() + "service_all_uninstall.bat";
        logger.info("[Uninstall Service] serviceAllUninstall()." + serviceAllUninstall);
        executeUtil.callCmd(serviceAllUninstall);
    }

    /**
     * 卸载所有exe(脚本路径：C:\\Program Files (x86)\\ChinaHotelHelp\\postinstall\\exe_all_uninstall.bat)
     */
    private void exeAllUninstall() {
        String exeAllUninstall = getUninstallPath() + "exe_all_uninstall.bat";
        logger.info("[Uninstall Service] exeAllUninstall()." + exeAllUninstall);
        executeUtil.callCmd(exeAllUninstall);
    }

}
