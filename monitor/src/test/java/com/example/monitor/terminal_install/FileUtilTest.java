package com.example.monitor.terminal_install;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUtilTest {
    @Autowired
    FileUtil fileUtil;
    @Test
    public void chinaHotelHelpIfExist() {
        System.out.println(fileUtil.chinaHotelHelpIfExist());
    }

    @Test
    public void downloadPackage() {
        System.out.println(fileUtil.downloadPackage(""));
    }

    @Test
    public void zipChinaHotelHelp() {
        System.out.println(fileUtil.zipChinaHotelHelp());
    }

    @Test
    public void unZipChinaHotelHelp() {
        System.out.println(fileUtil.unZipChinaHotelHelp("1.2.0.35-release-20190129000508 六盘水.zip"));
    }
}