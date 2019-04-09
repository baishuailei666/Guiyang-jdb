package com.example.monitor.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * 读取配置文件终端ID、版本号
 *
 * @author 白帅雷
 * @date 2019-01-22
 */
@Component
public class FileRead {
    private static Logger logger = Logger.getLogger(FileRead.class);
    @Autowired
    private PropertiesUtil propertiesUtil;

    /**
     * 终端ID配置文件路径
     */
    private String getPath() {
        return propertiesUtil.getValue("path");
    }

    /**
     * 版本号配置文件路径
     */
    private String getVersionPath() {
        return propertiesUtil.getValue("versionPath");
    }

    /**
     * 终端ID
     *
     * @return string
     */
    public String getTiId(){
        return getXMLTiId();
    }

    /**
     * 从配置文件中读取版本号
     *
     * @return string
     */
    public String getVersion(){
        String version = null;
        FileReader fileReader;
        try {
            fileReader = new FileReader(getVersionPath());
            String str;
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((str = bufferedReader.readLine()) != null) {
                version = str;
            }
            // 对结果进行分割获得版本号
            String[] results = Objects.requireNonNull(version).split(" ");
            version = results[2];
        } catch (IOException e) {
            logger.error("Err.读取版本号配置文件异常!" + e);
        }

        return version;
    }

    /**
     * 从XML文件中获得终端编号
     *
     * @return String
     */
    private String getXMLTiId() {
        String tiId = null;
        try {
            File f = new File(getPath() + "Main.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList nl = doc.getElementsByTagName("MACHINE_CODE");
            for (int i = 0; i < nl.getLength(); i++) {
                tiId = nl.item(i).getFirstChild().getNodeValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiId;
    }

}
