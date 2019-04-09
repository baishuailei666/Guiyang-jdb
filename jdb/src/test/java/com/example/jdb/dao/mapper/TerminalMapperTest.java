package com.example.jdb.dao.mapper;

import org.junit.Test;

import static org.junit.Assert.*;

public class TerminalMapperTest {

    @Test
    public void getList() {
        System.out.println(TerminalMapper.getList("3f4d7216c74b4f2f9cf564868f974ee7","",1,10));
    }
}