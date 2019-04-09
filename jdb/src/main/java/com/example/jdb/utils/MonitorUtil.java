//package com.example.jdb.utils;
//
//
//import com.sun.management.OperatingSystemMXBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.LineNumberReader;
//import java.lang.management.ManagementFactory;
//
///**
// * 运行监控工具类
// *
// * @author 白帅雷
// * @date 2019-01-09
// */
//@Component
//public class MonitorUtil {
//
//    @Autowired
//    private BytesUtil bytesUtil;
//
//    private static final int CPUTIME = 5000;
//    private static final int PRECENT = 100;
//    private static final int FAULTLENGTH = 10;
//
//    public static void main(String[] args) {
//        MonitorUtil monitorUtil = new MonitorUtil();
////        monitorUtil.getCpuRatioForWindows();
//        monitorUtil.getMonitorInfo();
//    }
//
//    /**
//     * 获取当前的监控对象
//     *
//     * @return
//     */
//    public void getMonitorInfo() {
//        int kb = 1024;
//
//
//        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//
//        System.out.println("System CPU。" + osmxb.getSystemCpuLoad());
//
//        System.out.println("Process CPU。" + osmxb.getProcessCpuLoad());
//
//        // 操作系统
//        String osName = System.getProperty("os.name");
//        System.out.println("osName。" + osName);
//
//        // 总的物理内存
//        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb /kb/kb;
//        System.out.println("totalMemorySize." + totalMemorySize);
//
//        // 剩余的物理内存
//        long freeMemorySize = osmxb.getFreePhysicalMemorySize() / kb /kb/kb;
//        System.out.println("freeMemorySize." + freeMemorySize);
//
//        // 已使用的物理内存
//        long usedMemorySize = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb/kb/kb;
//        System.out.println("usedMemorySize." + usedMemorySize);
//
//
//        File[] roots = File.listRoots();
//        for (File file : roots) {
//            System.out.println(file.getPath() + "信息如下:");
//            System.out.println("空闲 = " + file.getFreeSpace() / 1024 / 1024
//                    / 1024 + "G");// 空闲空间
//            System.out.println("可使用 = " + file.getUsableSpace() / 1024 / 1024
//                    / 1024 + "G");// 可用空间
//            System.out.println("总容量 = " + file.getTotalSpace() / 1024 / 1024
//                    / 1024 + "G");// 总空间
//            System.out.println();
//        }
//
//        double cpuRatio = this.getCpuRatioForWindows();
//        System.out.println("cpuRatio." + cpuRatio);
//
//    }
//
//
//    /**
//     * 获取CPU使用情况
//     *
//     * @return
//     */
//    private double getCpuRatioForWindows() {
//        try {
//            String procCmd = System.getenv("windir")
//                    + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
//                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
//
//            //取进程信息
//            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
//            Thread.sleep(CPUTIME);
//
//            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
//
//            if (c0 != null && c1 != null) {
//                // 闲置
//                long idleTime = c1[0] - c0[0];
//                // 运行
//                long busyTime = c1[1] - c0[1];
//
//                return (double) (PRECENT * (busyTime) / (busyTime + idleTime));
//            } else {
//                return 0.0;
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return 0.0;
//        }
//    }
//
//    /**
//     * 读取CPU
//     *
//     * @param proc
//     * @return
//     */
//    private long[] readCpu(final Process proc) {
//        long[] retn = new long[2];
//        try {
//            proc.getOutputStream().close();
//            InputStreamReader isr = new InputStreamReader(proc.getInputStream());
//            LineNumberReader lnr = new LineNumberReader(isr);
//            String line = lnr.readLine();
//            if (line == null || line.length() < FAULTLENGTH) {
//                return null;
//            }
//            int capidx = line.indexOf("Caption");
//            int cmdidx = line.indexOf("CommandLine");
//            int rocidx = line.indexOf("ReadOperationCount");
//            int umtidx = line.indexOf("UserModeTime");
//            int kmtidx = line.indexOf("KernelModeTime");
//            int wocidx = line.indexOf("WriteOperationCount");
//            long idleTime = 0;
//            long knelTime = 0;
//            long userTime = 0;
//            while ((line = lnr.readLine()) != null) {
//                if (line.length() < wocidx) {
//                    continue;
//                }
//                // 字段出现顺序：Caption,KernelModeTime,ReadOperationCount,
//                // ThreadCount,UserModeTime,WriteOperation
//                String caption = bytesUtil.subString(line, capidx, cmdidx - 1).trim();
//                String cmd = bytesUtil.subString(line, cmdidx, kmtidx - 1).trim();
//                if (cmd.contains("wmic.exe")) {
//                    continue;
//                }
//
//                String s1 = bytesUtil.subString(line, kmtidx, rocidx - 1).trim();
//                String s2 = bytesUtil.subString(line, umtidx, wocidx - 1).trim();
//                if (caption.equals("System Idle Process") || caption.equals("System")) {
//                    if (s1.length() > 0) {
//                        idleTime += Long.valueOf(s1);
//                    }
//
//                    if (s2.length() > 0) {
//                        idleTime += Long.valueOf(s2);
//                    }
////                    idleTime += Long.valueOf(bytesUtil.subString(line, kmtidx, rocidx - 1).trim());
////                    idleTime += Long.valueOf(bytesUtil.subString(line, umtidx, wocidx - 1).trim());
//                    continue;
//                }
//
//                if (s1.length() > 0) {
//                    knelTime += Long.valueOf(s1);
//                }
//                if (s2.length() >0) {
//                    userTime += Long.valueOf(s2);
//                }
////                knelTime += Long.valueOf(bytesUtil.subString(line, kmtidx, rocidx - 1).trim());
////                userTime += Long.valueOf(bytesUtil.subString(line, umtidx, wocidx - 1).trim());
//            }
//            retn[0] = idleTime;
//            retn[1] = knelTime + userTime;
//
//            return retn;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                proc.getInputStream().close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//}
