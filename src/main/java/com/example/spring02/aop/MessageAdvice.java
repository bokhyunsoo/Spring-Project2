package com.example.spring02.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect // aop를 지원하는 advice
public class MessageAdvice {
	private static final Logger logger = LoggerFactory.getLogger(MessageAdvice.class);
// MessageService로 시작하는 클래스의 모든 method
//실행전에 경유하는 코드
	@Before(
			"execution(* "
			+ " com.example.spring02.service.message"
			+ ".MessageService*.*(..))")
	public void startLog(JoinPoint jp) {
		logger.info("핵심 업무 코드의 정보:"+jp.getSignature());
		logger.info("method:"+jp.getSignature().getName());
		logger.info("arguments:"+Arrays.toString(jp.getArgs()));
	}
	
	@Around("execution(* "
			+ " com.example.spring02.service.message"
			+ ".MessageService*.*(..) )")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		//핵심업무 수행전
		long start = System.currentTimeMillis();
		Object result = pjp.proceed();//핵심업무 수행
		//핵심업무 수행후
		long end = System.currentTimeMillis();
		logger.info(pjp.getSignature().getName()+":"+(end-start));
		logger.info("===================");
		return result;
	}
}
