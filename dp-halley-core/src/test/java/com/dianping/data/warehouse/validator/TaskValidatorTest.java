package com.dianping.data.warehouse.validator;

import com.dianping.data.warehouse.domain.TaskDO;
import com.dianping.data.warehouse.halley.domain.TaskSQLParserDO;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by hongdi.tang on 14-3-26.
 */
public class TaskValidatorTest {
    private static TaskDO mockData(){
        TaskDO task = new TaskDO();
        task.setSuccessCode("3424");
        task.setCycle("M");
        task.setIfPre(1);
        task.setIfRecall(1);
        task.setIfWait(1);
        task.setOffsetType("offset");
        task.setOffset("D0sefse");
        task.setType(3);
        task.setWaitCode("3423;4564;234");
        task.setRecallCode("234;345234");
        task.setRecallLimit(8);
        return task;
    }


    @Test
    public void testValidateOffsetType() throws Exception{
        Method method = TaskValidator.class.getDeclaredMethod("validateOffsetType", String.class,String.class);
        method.setAccessible(true);
        String[] output = (String[]) method.invoke(TaskValidator.class, "offset","M02");
        System.out.println(output[1]);
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[1],null);
        String[] output1 = (String[]) method.invoke(TaskValidator.class, "offset","D023");
        System.out.println(output[1]);
        Assert.assertSame(output1[0],"1");
        Assert.assertSame(output1[0],"1");
        Assert.assertSame(output1[1],null);
    }

    @Test
    public void testValidateWait() throws Exception{
        Method method = TaskValidator.class.getDeclaredMethod("validateWait", Integer.class,String.class);
        method.setAccessible(true);
        String[] output = (String[]) method.invoke(TaskValidator.class, 1,"3324sdrg24;5drsg46;234");
        System.out.println(output[1]);
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[1],null);
        String[] output1 = (String[]) method.invoke(TaskValidator.class, 0,"23as4;546;234");
        System.out.println(output[1]);
        Assert.assertSame(output1[0],"1");
        Assert.assertSame(output1[1],null);
    }

    @Test
    public void testValidateTaskType() throws Exception{
        Method method = TaskValidator.class.getDeclaredMethod("validateTaskType", Integer.class);
        method.setAccessible(true);
        String[] output = (String[]) method.invoke(TaskValidator.class, 1);
        System.out.println(output[1]);
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[1],null);

        String[] output1 = (String[]) method.invoke(TaskValidator.class, 3);
        System.out.println(output1[1]);
        Assert.assertSame(output1[0], "0");
        Assert.assertNotNull(output1[1]);
    }

    @Test
    public void testValidateRecall() throws Exception{
        Method method = TaskValidator.class.getDeclaredMethod("validateRecall", Integer.class,Integer.class);
        method.setAccessible(true);
        String[] output = (String[]) method.invoke(TaskValidator.class, 0,3);
        System.out.println(output[1]);
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[1],null);

        String[] output1 = (String[]) method.invoke(TaskValidator.class, 2,5);
        System.out.println(output1[1]);
        Assert.assertSame(output1[0], "0");
        Assert.assertNotNull(output1[1]);
    }

    @Test
    public void testValidateCodes() throws Exception{
        Method method = TaskValidator.class.getDeclaredMethod("validateCodes", String.class);
        method.setAccessible(true);
        String[] output = (String[]) method.invoke(TaskValidator.class, "234234234;234;2342");
        System.out.println(output[1]);
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[1],null);

        String[] output1 = (String[]) method.invoke(TaskValidator.class, ";;");
        System.out.println(output1[1]);
        Assert.assertSame(output1[0], "0");
        Assert.assertNotNull(output1[1]);

    }

    @Test
    public void testValidateCycle() throws Exception{
        Method method = TaskValidator.class.getDeclaredMethod("validateCodes", String.class);
        method.setAccessible(true);
        String[] output = (String[]) method.invoke(TaskValidator.class, "234234234;234;2342");
        System.out.println(output[1]);
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[1],null);

        String[] output1 = (String[]) method.invoke(TaskValidator.class, ";;");
        System.out.println(output1[1]);
        Assert.assertSame(output1[0], "0");
        Assert.assertNotNull(output1[1]);

    }

    @Test
    public void testValidateTask() throws Exception{
        TaskDO task = mockData();
        String[] output = TaskValidator.validateTask(task);
        Assert.assertSame(output[0],"1");
        Assert.assertSame(output[1],null);
    }

    @Test
    public void testValidateType() throws Exception{
        TaskDO task = mockData();
        task.setType(3);
        String[] rtn = TaskValidator.validateTask(task);

    }

}
