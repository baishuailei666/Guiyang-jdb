package com.example.jdb.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileUtilTest {


    @Test
    public void downFile() {
    }

    @Test
    public void upFile() {
    }

    @Test
    public void unZip() {
        FileUtil fileUtil = new FileUtil();
        System.out.println("unZip." + fileUtil.unZip("D:\\logs\\logs.zip", "D:\\logs\\zip\\"));
    }
}