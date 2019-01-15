package com.seckkill.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法并返回结果
    public ValidationResult validate(Object bean){
        final ValidationResult result=new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet=validator.validate(bean);
        if (constraintViolationSet.size()>0){
            //有错误
            result.setError(true);
            for(ConstraintViolation<Object> constraintViolation : constraintViolationSet) {
                String errorMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errorMsg);
            }
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //通过工厂方法获取Hibernate Validator实例
        this.validator=Validation.buildDefaultValidatorFactory().getValidator();
    }
}
