import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;


public class test {

    public void getMonitorInfo() throws Exception {
        int kb = 1024;

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println("1---" + osmxb.getProcessCpuLoad() * 100 + " %");
        System.out.println("2---" + osmxb.getSystemCpuLoad() * 100 +" %");
        
        String osName = System.getProperty("os.name");
        System.out.println("osName---" + osName);

        long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb /kb/kb;
        System.out.println("totalMemorySize---" + totalMemorySize + " G");
       
        long freeMemorySize = osmxb.getFreePhysicalMemorySize() / kb/kb/kb;
        System.out.println("freeMemorySize---" + freeMemorySize + " G");

        long usedMemorySize = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / kb/kb/kb;
        System.out.println("usedMemorySize---" + usedMemorySize + " G");
        
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent());

        int totalThread = parentThread.activeCount();

    }

    public static void main(String[] args) throws Exception {

        test test = new test();
        test.getMonitorInfo();

        for (int i = 0; i <= 10; i++) {
            test.getMonitorInfo();

            System.out.println("====================");
            Thread.sleep(5000);
        }
    }
}