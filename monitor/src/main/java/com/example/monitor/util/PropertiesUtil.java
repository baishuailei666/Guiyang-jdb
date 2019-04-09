package com.example.monitor.util;

import org.springframework.stereotype.Component;

import java.util.ResourceBundle;
/**
 * 读取配置文件
 *
 * @author 白帅雷
 * @date 2019-01-22
 */
@Component
public class PropertiesUtil {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("monitor");

    /**
     * 读取配置文件内容
     *
     * @param key key
     * @return String
     */
    public String getValue(String key) {
        return resourceBundle.getString(key);
    }
}
