package com.example.monitor.system_info;

import java.text.DecimalFormat;

/**
 * 资源监控
 *
 * @author 白帅雷
 * @date 2019-01-22
 */
public class SystemInfo {

    /**
     * 系统总内存
     */
    private long systemTotalMem;

    /**
     * 系统使用内存
     */
    private long systemMemUse;

    /**
     * 系统使用总CPU
     */
    private double cpuUse;

    /**
     * 进程使用内存
     */
    private long appMemUse;

    /**
     * 进程使用CPU
     */
    private double appCpuUse;

    /**
     * 系统磁盘
     */
    private long diskSize;

    /**
     * 进程使用磁盘
     */
    private long diskUsedSize;

    /**
     * 监控时间
     */
    private long monitorTime;

    /**
     * 终端ID
     */
    private String tiId;

    public long getSystemTotalMem() {
        return systemTotalMem;
    }

    public void setSystemTotalMem(long systemTotalMem) {
        this.systemTotalMem = systemTotalMem;
    }

    public long getSystemMemUse() {
        return systemMemUse;
    }

    public void setSystemMemUse(long systemMemUse) {
        this.systemMemUse = systemMemUse;
    }

    public double getCpuUse() {
        return cpuUse;
    }

    public void setCpuUse(double cpuUse) {
        this.cpuUse = cpuUse;
    }

    public long getAppMemUse() {
        return appMemUse;
    }

    public void setAppMemUse(long appMemUse) {
        this.appMemUse = appMemUse;
    }

    public double getAppCpuUse() {
        return appCpuUse;
    }

    public void setAppCpuUse(double appCpuUse) {
        this.appCpuUse = appCpuUse;
    }

    public long getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(long diskSize) {
        this.diskSize = diskSize;
    }

    public long getDiskUsedSize() {
        return diskUsedSize;
    }

    public void setDiskUsedSize(long diskUsedSize) {
        this.diskUsedSize = diskUsedSize;
    }

    public long getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(long monitorTime) {
        this.monitorTime = monitorTime;
    }

    public String getTiId() {
        return tiId;
    }

    public void setTiId(String tiId) {
        this.tiId = tiId;
    }

    @Override
    public String toString() {
        DecimalFormat format1 = new DecimalFormat("0.0000");
        long M = 1024*1024;
        String result;
        result = "SystemInfo{" +
                "tiId=" + tiId +
                "monitorTime=" + monitorTime +
               "systemTotalMem=" + (systemTotalMem / M) + "M" +
               ", systemMemUse=" + (systemMemUse / M) + "M" +
               ", appMemUse=" + (appMemUse/M) + "M" +
               ", cpuUse=" + format1.format(cpuUse) +
               ", appCpuUse=" + format1.format(appCpuUse) +
               ", diskSize=" + (diskSize/M) + "G" +
               ", diskUsedSize=" + (diskUsedSize/M) +"G" +
               '}';
        return result;
    }

}
