package com.example.monitor.terminal_install;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 执行bat脚本
 *
 * @author 白帅雷
 * @date 2019-01-29
 */
@Component
public class ExecuteUtil {
    private static Logger logger = Logger.getLogger(ExecuteUtil.class);

    public static void main(String[] args) {
        ExecuteUtil executeUtil = new ExecuteUtil();
        executeUtil.callCmd("D:\\jdb\\main\\install-package\\1.2.0.35-release-20190129000508\\installAll.bat");
    }

    /**
     * 执行bat脚本
     *
     * @param locationCmd 脚本路径
     */
    void callCmd(String locationCmd) {

        String batFilePath = "cmd.exe /c " + locationCmd;
        logger.info("[执行bat脚本] batFilePath." + batFilePath);
        try{
            // 执行命令
            Process process = Runtime.getRuntime().exec(batFilePath);
            // 获得命令结果的输出流
            InputStream in = process.getInputStream();
            // 读输出流类去读，并用缓冲器读行
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String str ;
            while ((str = br.readLine()) != null) {
                logger.info("脚本执行的结果: " + str);
            }

            try{
                process.waitFor();
                br.close();
                in.close();
            } catch (IOException | InterruptedException e) {
                logger.error("Err.A执行脚本文件异常!" + e);
            }
        } catch (IOException e1) {
            logger.error("Err.B执行脚本文件异常!" + e1);
        }
    }


}
