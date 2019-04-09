package com.example.jdb.utils;

import com.example.jdb.dao.WarningLogDao;
import com.example.jdb.entity.WarningLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 文件工具类 提供日志下载
 * @author 白帅雷
 * @date 2019-01-10
 */
@Component
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    @Autowired
    private WarningLogDao warningLogDao;

    /**
     * 下载异常日志
     *
     * @param pkId pkId
     */
    public Map<String, Object> downFile(HttpServletResponse response, int pkId) {
        Map<String, Object> map = new HashMap<>(1);
        WarningLog warningLog = warningLogDao.getWarningLogsById(pkId);
        // 日志存储路径
        String logPath = warningLog.getLogPath();
        // 日志存储名称
        String replace = warningLog.getMonitorTime().replaceAll(" ","-").substring(0, 19);
        String fileName = warningLog.getTiId() + "_" + replace.replaceAll(":","-");
        // 日志文件
        File file = new File(logPath + "\\" + fileName + ".txt");
        System.out.println("-" + File.separator);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int len;
            while ((len = bis.read(buff)) != -1) {
                os.write(buff, 0, len);
                os.flush();
            }
            map.put("status", 200);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Err.下载异常日志!" + e);
            map.put("Err", e.toString());
            response.setStatus(500);
            return map;
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Err.关闭输入流资源!" + e);
                map.put("Err", "关闭输入流资源异常!" + e);
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Err.关闭输出流资源!" + e);
                map.put("Err", "关闭输出流资源异常!" + e);
            }
        }
        return map;
    }

    /**
     * 上传ZIP文件并解压
     *
     * @param data
     * @param fileName
     * @return
     */
    public Map<String, Object> upZipFile(byte[] data, String fileName) {
        // 获取文件名中最后一次出现"."号的位置
        int index = fileName.lastIndexOf(".");
        // 获取文件的后缀
        String prefix = fileName.substring(index+1);
        // 判断必须包含"."号，且不能出现在首位，同时后缀名为"zip"
        if (index != -1 && index != 0 && prefix.equals("zip")) {
            logger.info("判断是ZIP文件!");
            List<byte[]> zipDataList = unZip(data);
            Map<String, Object> map = new HashMap<>(1);
            zipDataList.forEach(bytes -> {
                logger.info("解压ZIP文件后每一个文件bytes.length:" + bytes.length);
                upFile(bytes, fileName);
            });
            map.put("status", 200);
            return map;
        } else {
            return upFile(data, fileName);
        }
    }

    /**
     * 上传文件
     *
     * @param data
     * @param fileName
     * @return
     */
    public Map<String, Object> upFile(byte[] data, String fileName) {
        logger.info("[上传文件!] data.length:" + data.length + " fileName:" + fileName);
        Map<String, Object> map = new HashMap<>(1);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        try {
            File file = new File("D:\\logs\\" + System.currentTimeMillis() + fileName);
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
            bis = new BufferedInputStream(byteInputStream);
            // 获取文件的父路径字符串
            File path = file.getParentFile();
            logger.info("path." + path);
            if (!path.exists()) {
                logger.info("文件夹不存在，创建目录! path=" + path);
                boolean isCreated = path.mkdirs();
                if (!isCreated) {
                    logger.error("创建目录失败! path=" + path);
                }
            }
            fos = new FileOutputStream(file);
            // 实例化OutputString 对象
            output = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                output.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            output.flush();
            map.put("status", 200);
        } catch (Exception e) {
            map.put("Err", "输出文件流时抛异常!" + e);
            logger.error("Err.输出文件流时抛异常!", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                assert fos != null;
                fos.close();
                Objects.requireNonNull(output).close();
            } catch (IOException e0) {
                map.put("Err", "文件处理失败!" + e0);
                logger.error("Err.文件处理失败!", e0);
            }
        }

        return map;
    }


    /**
     * 解压ZIP文件
     *
     * @param zipName 目标zip文件
     * @param descDir 指定解压目录
     * @return
     */
    boolean unZip(String zipName, String descDir) {
        boolean flag = false;

        // zip文件
        File zipFile = new File(zipName);
        // 解压到指定目录
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile unZip = null;
        try {
            // 指定编码，否则压缩包里面不能有中文目录
            unZip = new ZipFile(zipFile, Charset.forName("gbk"));
            // 遍历压缩文件中的文件
            for(Enumeration entries = unZip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = unZip.getInputStream(entry);
                String outFilePath = (descDir+zipEntryName).replace("/", File.separator);
                logger.info("outPath." + outFilePath);
                // 判断解压缩文件的路径是否存在,不存在则创建文件路径
                File file = new File(outFilePath.substring(0, outFilePath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                logger.info("file." + file);
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outFilePath).isDirectory()){
                    continue;
                }

                OutputStream out = new FileOutputStream(outFilePath);
                byte[] buf1 = new byte[2048];
                int len;
                while((len=in.read(buf1)) > 0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
            }
            flag = true;
            // 必须关闭，否则无法删除该zip文件
            unZip.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Err.解压ZIP文件异常!" + e);
        }
        return flag;
    }

    /***
     * 解压Zip
     *
     * @param data
     * @return
     */
    private static List<byte[]> unZip(byte[] data) {
        logger.info("[开始解压ZIP文件] data:" + data.length);
        List<byte[]> list = new ArrayList<>(1);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            int index = 0;
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    bos.write(buf, 0, num);
                }
                byte[] result = bos.toByteArray();
                list.add(result);
                index ++;
                logger.info("[正在解压ZIP文件] index:" + index + " result.length:" + result.length);
                // 保存成文件
//                FileOutputStream out = new FileOutputStream("D:\\" + System.currentTimeMillis()+ ".txt");
//                out.write(result);

                bos.flush();
                bos.close();
            }
            zip.close();
            bis.close();
            logger.info("[解压ZIP结束! 文件大小.index:]" + index);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Err.解压ZIP文件异常!" + ex);
        }
        return list;
    }
}
