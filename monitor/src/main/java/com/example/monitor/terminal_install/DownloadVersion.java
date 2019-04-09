package com.example.monitor.terminal_install;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 从云端下载相应版本主程序
 *
 * @author 白帅雷
 * @date 2019-01-29
 */
@Component
public class DownloadVersion {

    private static Logger logger = Logger.getLogger(DownloadVersion.class);

    public Map<String, Object> downVersion(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(1);
        String fileName = "";
        // 日志文件
        File file = new File( fileName + ".txt");
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
}
