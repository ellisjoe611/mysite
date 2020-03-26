package com.douzone.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {

	@Around("execution(* *..*.repository.*.*(..)) || execution(* *..*.service.*.*(..)) || execution(* *..*.controller.*.*(..))")
	public Object arountAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// before
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object result = pjp.proceed();

		// after
		stopWatch.stop();
		Long totalTime = stopWatch.getTotalTimeMillis();

		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String task = className + "." + methodName;
		System.out.println("[" + task + "] took " + totalTime + " MilliSeconds.");

		return result;
	}

}
