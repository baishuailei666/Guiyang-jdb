package com.example.monitor.terminal_install;

import com.example.monitor.util.PropertiesUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具（1、判断主程序是否存在；2、压缩原有主程序；3、解压版本主程序）
 *
 * @author 白帅雷
 * @date 2019-01-29
 */
@Component
public class FileUtil {

    @Autowired
    private PropertiesUtil propertiesUtil;
    @Autowired
    private RestTemplate restTemplate;

    private static final int  BUFFER_SIZE = 2 * 1024;
    private static Logger logger = Logger.getLogger(FileUtil.class);

    /**
     * 判断主程序是否存在(首次安装)
     *
     * @return boolean
     */
    boolean chinaHotelHelpIfExist() {
        String chinaHotelHelpPath = propertiesUtil.getValue("chinaHotelHelp");
        logger.info("[判断主程序目录是否存在]chinaHotelHelpPath." + chinaHotelHelpPath);
        File file = new File(chinaHotelHelpPath);
        return file.exists();
    }


    /**
     * 压缩文件下载路径
     *
     * @param downloadUrl 下载版本主程序地址
     * @return 压缩文件名
     */
    String downloadPackage(String downloadUrl){
        String packageName;
        String downloadPath = propertiesUtil.getValue("downloadPath");
        File file = new File(downloadPath);
        if (!file.exists() && !file.isDirectory()) {
            // 创建下载存储文件夹
            file.mkdirs();
        }
        // TODO 下载版本主程序


        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(downloadUrl, Map.class);
        int result = responseEntity.getStatusCodeValue();
        if (result == 200) {
            responseEntity.getBody();
            packageName = "";

            return packageName;
        }
        return null;
    }

    /**
     * 压缩(备份)原有主程序
     *
     * @return boolean
     */
    boolean zipChinaHotelHelp() {
        boolean isZip = false;
        File sourceFile = new File(propertiesUtil.getValue("chinaHotelHelp"));
        ZipOutputStream zos;
        try {
            String backUpPath = propertiesUtil.getValue("backUpPath");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String bakName = dateFormat.format(System.currentTimeMillis());
            logger.info("[压缩(备份)主程序文件]backUpPath." + backUpPath);
            zos = new ZipOutputStream(new FileOutputStream(new File(backUpPath + bakName + "Bak.zip")));
            compress(sourceFile, zos, bakName + "Bak.zip", true);
            isZip = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isZip;
    }

    /**
     * 解压主程序压缩文件
     *
     * @return boolean
     */
    boolean unZipChinaHotelHelp(String packageName) {
        // TODO 主程序压缩文件名
        String downLoadPath = propertiesUtil.getValue("downloadPath");
        logger.info("[解压主程序压缩文件]downLoadPath." + downLoadPath);
        String zipPathName = downLoadPath + packageName;
        String descDir = downLoadPath + packageName.split(".zip")[0];
        return unZip(zipPathName, descDir);
    }

    /**
     * 解压ZIP文件
     *
     * @param zipName 目标zip文件
     * @param descDir 指定解压目录
     * @return boolean
     */
    private boolean unZip(String zipName, String descDir) {
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
                String outFilePath = (descDir + "\\" + zipEntryName).replace("/", File.separator);
                logger.info("[解压主程序压缩文件包]outPath." + outFilePath);
                // 判断解压缩文件的路径是否存在,不存在则创建文件路径
                File file = new File(outFilePath.substring(0, outFilePath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }

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
            logger.error("Err.解压主程序文件异常!" + e);
        }
        return flag;
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{

        byte[] buf = new byte[BUFFER_SIZE];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }
}
