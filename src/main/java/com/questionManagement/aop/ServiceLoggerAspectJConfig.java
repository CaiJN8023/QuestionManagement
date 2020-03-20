package com.questionManagement.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author CJN
 * @date 2019年3月15日
 * Title: ServiceLoggerAspectJConfig 
 * Description: 所有以ServiceImpl 结尾的类中的public 方法都会被记录入参、返回值及异常情况
 */
@Aspect
@Component
@Slf4j
public class ServiceLoggerAspectJConfig {

    @Pointcut(value = "execution(public * com.questionManagement.service.impl.*ServiceImpl.*(..))")
    public void serviceLog() { }
    

    /**
     * Title: beforeServiceLog
     * Description: 进入方法前要做的事情：记录方法名和入参
     * @param joinPoint
     */
    @Before(value = "serviceLog()")
    public void beforeServiceLog(JoinPoint joinPoint) {
    	Object[] args = joinPoint.getArgs();
    	String methodName = joinPoint.getSignature().toShortString();
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(methodName);
    	sb.append("--entry args: " + Arrays.toString(args));
    	log.info(sb.toString());
    	
    }

    /**
     * Title: afterServiceLog
     * Description:  切点所在函数正常返回之后要做的事情：记录方法名和返回值
     * @param joinPoint 切入点
     * @param returnObject	返回值（返回的数据）
     */
    @AfterReturning(value = "serviceLog()", returning = "returnObj")
    public void afterServiceLog(JoinPoint joinPoint, Object returnObj) {
    	String methodName = joinPoint.getSignature().toShortString();
    	StringBuffer sb = new StringBuffer(methodName);
    	sb.append("--return object: ");
    	if(returnObj == null) {
    		sb.append("null");
    	} else {
    		sb.append(returnObj.toString());
    	}
    	log.info(sb.toString());
    }
    
    /**
     * Title: handlerExcetionLog
     * Description: 出现异常要做的事情：记录方法名，异常类型和描述
     * @param joinPoint	切点
     * @param exception	异常
     */
    @AfterThrowing(value = "serviceLog()", throwing = "exception")
    public void handlerExceptionLog(JoinPoint joinPoint, Exception exception) {
    	String methodName = joinPoint.getSignature().toShortString();
    	StringBuffer sb = new StringBuffer(methodName);
    	sb.append("--exception msg: " + exception.getMessage());
    	log.info(sb.toString());
    }
}