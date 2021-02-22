package com.uitgis.kras.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class LoggerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("execution(* com.uitgis.kras..*Controller.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String name = joinPoint.getSignature().getDeclaringTypeName();		
		
		// 
		logger.info(">>>>> Start: " + request.getRequestURI() + " : " + name + "." + joinPoint.getSignature().getName() + "()");
		Object proceed = joinPoint.proceed();
		// return 후 작업
		logger.info(">>>>> End: " + request.getRequestURI() + " : " + name + "." + joinPoint.getSignature().getName() + "()");
		
		return proceed;
	}
	
	@Around("@annotation(LogExecutionTime)")
	public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        Object proceed = joinPoint.proceed();
        
        stopWatch.stop();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        logger.info(">>>>> " + request.getRequestURI() + " > " + stopWatch.shortSummary());
        
        return proceed;
	}
}
