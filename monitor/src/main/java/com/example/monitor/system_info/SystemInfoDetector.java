package com.example.monitor.system_info;

import com.example.monitor.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.hyperic.sigar.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 资源监控
 *
 * @author 白帅雷
 * @date 2019-01-22
 */
@Component
public class SystemInfoDetector {

    private static Logger logger = Logger.getLogger(SystemInfoDetector.class);
    @Autowired
    PropertiesUtil propertiesUtil;

    /**
     * 监控程序服务名称
     */
    private String getServiceName() {
        return propertiesUtil.getValue("serviceName");
    }

    /**
     * 获取运行资源
     *
     * @return SystemInfo
     */
    SystemInfo detect() {
        Sigar sigar = new Sigar();
        SystemInfo systemInfo = new SystemInfo();
        long usdPid = 0;
        long[] pidList;
        try {
            // 所有进程pid
            pidList = sigar.getProcList();
            for (long pid : pidList) {
                ProcState prs = sigar.getProcState(pid);
                // 指定进程名称
                if (prs.getName().equals(getServiceName())) {
                    usdPid = pid;
                }
            }
            // 监控时间
            systemInfo.setMonitorTime(System.currentTimeMillis());
            ProcMem procMem = sigar.getProcMem(usdPid);
            // 进程使用内存
            systemInfo.setAppMemUse(procMem.getResident());
            // 进程使用CPU
            sigar.getProcCpu(usdPid).getPercent();
            // 使用sleep 再取值会有数值, 还要除以cpu核数(占总的cpu)
            Thread.sleep(1500);
            double appUseCpu = sigar.getProcCpu(usdPid).getPercent();
            // 进程使用CPU
            systemInfo.setAppCpuUse(appUseCpu / 4);
            // 系统内存
            systemInfo.setSystemTotalMem(sigar.getMem().getTotal());
            // 系统使用内存
            systemInfo.setSystemMemUse(sigar.getMem().getUsed());
            // 系统CPU
            CpuPerc cpuPerc = sigar.getCpuPerc();
            systemInfo.setCpuUse(cpuPerc.getCombined());
            // 磁盘
            String osName = System.getProperties().getProperty("os.name").toLowerCase();
            if (osName.contains("window")) {
                // windows系统，判断盘符大小
                String cwd = sigar.getProcExe(usdPid).getCwd();
                char fsName = cwd.charAt(0);
                FileSystemUsage usage = sigar.getFileSystemUsage(fsName+":\\");
                systemInfo.setDiskSize(usage.getTotal());
                systemInfo.setDiskUsedSize(usage.getUsed());
            } else if(osName.contains("linux")){
                // linux系统，判断整个大小
                FileSystemUsage usage = sigar.getFileSystemUsage("/");
                systemInfo.setDiskSize(usage.getTotal());
                systemInfo.setDiskUsedSize(usage.getAvail());
            }

        } catch (SigarException | InterruptedException e) {
            logger.error("Err.获取运行资源异常!" + e);
        }

        return systemInfo;
    }

}
