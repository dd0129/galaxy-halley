package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.halley.domain.TaskOwnerDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.annotation.Resources;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-applicationcontext.xml"})
public class InstanceServiceImplTest {

    @Resource
    private InstanceServiceImpl service;

    @Test
    public void testGetOncallOwner() throws Exception {
        TaskOwnerDO owner = service.getOncallOwner(10060);
        System.out.println(owner.getMail());
        System.out.println(owner.getPhone());
        System.out.println(owner.getPinyinName());
    }
}