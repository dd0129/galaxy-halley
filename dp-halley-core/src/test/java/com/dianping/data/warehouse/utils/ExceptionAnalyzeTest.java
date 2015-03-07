package com.dianping.data.warehouse.utils;

import com.dianping.data.warehouse.domain.ExceptionAlertDO;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-applicationcontext-resource.xml"})
public class ExceptionAnalyzeTest {

    @Resource(name = "exceptionService")
    private ExceptionAnalyze analyze;

    @Test
    public void testAnalyze() throws Exception {
        File file = new File("/Users/Sunny/Desktop/test.txt");
        try {
            FileInputStream in = new FileInputStream(file);
            ExceptionAlertDO alertDO = analyze.analyze(in, "wormhole");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
