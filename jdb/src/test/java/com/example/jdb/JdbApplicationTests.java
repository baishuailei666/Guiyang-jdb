package com.example.jdb;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbApplicationTests {
    private static final Logger logger = Logger.getLogger(JdbApplicationTests.class);

    @Test
    public void contextLoads() {
        for (int i=0; i<20; i++) {
            logger.debug(" This is a DEBUG message!");
            logger.info(" This is INFO message!");
            logger.warn(" This is a WARN message!");
            logger.error(" This is ERROR message!");
        }

    }

}

