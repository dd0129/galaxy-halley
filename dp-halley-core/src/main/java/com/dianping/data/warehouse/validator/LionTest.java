package com.dianping.data.warehouse.validator;

import com.dianping.data.warehouse.utils.LionUtil;

/**
 * Created by hongdi.tang on 2014/9/11.
 */
public class LionTest {
    public static void main(String[] args){
        System.out.println(LionUtil.getProperty(args[0]));
        System.out.println(LionUtil.getIntProperty(args[0]));
    }
}
