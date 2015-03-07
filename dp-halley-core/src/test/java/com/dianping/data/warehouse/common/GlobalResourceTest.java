package com.dianping.data.warehouse.common;

import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hongdi.tang on 14-3-26.
 */
public class GlobalResourceTest {
    private static Logger logger = LoggerFactory.getLogger(GlobalResourceTest.class);


    @Test
    public void testPrint() throws Exception {
        //GlobalResource res = new GlobalResource();
        //res.print();
        logger.info(String.valueOf(GlobalResource.ENV_PROPS.size()));
        Assert.assertSame(GlobalResource.DEPLOY_HOME, System.getenv("deploy_home"));
        Assert.assertNotNull(GlobalResource.DEPLOY_HOME);
        Assert.assertNotNull(GlobalResource.ENV_PROPS);
        Assert.assertSame(GlobalResource.ENV_PROPS.size(),7);
        System.out.println("fuck");
    }
}
