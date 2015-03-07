package com.dianping.data.warehouse.halley.domain;

import junit.framework.TestCase;

public class TaskChildToParentDOTest extends TestCase {

    public void testSetParentEndTime() throws Exception {
        TaskChildToParentDO parentDO = new TaskChildToParentDO();

        parentDO.setParentEndTime(new Long("1234234"));
        System.out.println(parentDO.getParentEndTime());
    }
}