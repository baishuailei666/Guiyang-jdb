package com.example.monitor.terminal_install;

import com.example.monitor.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 安装服务（安装程序、安装服务、启动服务）
 *
 * @author 白帅雷
 * @date 2019-01-29
 */
@Component
public class InstallService {

    @Autowired
    private PropertiesUtil propertiesUtil;
    @Autowired
    private ExecuteUtil executeUtil;
    @Autowired
    private FileUtil fileUtil;
    private static Logger logger = Logger.getLogger(InstallService.class);

    /**
     * 获得安装路径
     *
     * @return String
     */
    private String getInstallPath() {
        return propertiesUtil.getValue("installPath");
    }

    /**
     * 获得下载压缩文件路径
     *
     * @return String
     */
    private String getDownloadPath() {
        return propertiesUtil.getValue("downloadPath");
    }

    /**
     * 安装服务（安装程序、安装服务、启动服务）
     * @param packageName 版本压缩包名称
     */
    void installService(String packageName) {
        // 解压ZIP主程序
        boolean unZipChinaHotelHelp = fileUtil.unZipChinaHotelHelp(packageName);
        if (unZipChinaHotelHelp) {
            // 安装程序
            installAll(packageName);
            // 安装服务
            serviceAllInstall();
            // 启动服务
            serviceAllStart();
        }
    }

    /**
     * 安装程序(脚本路径：D:\\jdb\\main\\install-package\\packageName\\installAll.bat)
     *
     * @param packageName 压缩文件名称
     */
    private void installAll(String packageName) {
        String installAll = getDownloadPath() + packageName +"\\installAll.bat";
        logger.info("[Install Service] installAll()." + installAll);
        executeUtil.callCmd(installAll);
    }

    /**
     * 安装服务(脚本路径：C:\\Program Files (x86)\\ChinaHotelHelp\\postinstall\\services_all_install.bat)
     */
    private void serviceAllInstall() {
        String serviceAllInstall = getInstallPath() + "postinstall\\services_all_install.bat";
        logger.info("[Install Service] serviceAllInstall()." + serviceAllInstall);
        executeUtil.callCmd(serviceAllInstall);
    }

    /**
     * 启动服务(脚本路径：C:\\Program Files (x86)\\ChinaHotelHelp\\postinstall\\services_all_start.bat)
     */
    private void serviceAllStart() {
        String serviceAllStart = getInstallPath() + "postinstall\\services_all_start.bat";
        logger.info("[Install Service] serviceAllStart()." + serviceAllStart);
        executeUtil.callCmd(serviceAllStart);
    }


}
