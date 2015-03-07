package com.dianping.data.warehouse.core.common;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Iterator;

/**
 * Created by hongdi.tang on 14-2-27.
 */
public class ValidatorUtils {
    public static boolean validateModel(Object obj) {
        StringBuffer buffer = new StringBuffer();
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();

        for (ConstraintViolation<Object> constraint : validator.validate(obj)) {
            String message = constraint.getMessage();
            buffer.append(message).append("\n");
        }
        if(StringUtils.isBlank(buffer.toString())){
            return true;
        }else{
            throw new ValidationException(buffer.toString());
        }
    }

    public static boolean validateProperties(Object obj,String... properties){
        StringBuffer buffer = new StringBuffer();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (String property : properties){
            Iterator<ConstraintViolation<Object>> iter = validator.validateProperty(obj, property).iterator();
            while (iter.hasNext()) {
                String message = iter.next().getMessage();
                buffer.append(message);
            }
        }

        if(StringUtils.isBlank(buffer.toString())){
            return true;
        }else{
            throw new ValidationException(buffer.toString());
        }
    }
}
