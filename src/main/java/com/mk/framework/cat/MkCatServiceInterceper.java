package com.mk.framework.cat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

@Aspect
@Component
public class MkCatServiceInterceper {

	@Pointcut("execution(* com.mk..*.service.*Service*.*(..))")
	public void servicePointcut() {
	}

	@Around("servicePointcut()")
	public Object around(ProceedingJoinPoint proceeding) throws Throwable {

		Object resultObj = null;
		String methodName = null;
		String className = null;

		className = proceeding.getTarget().getClass().getSimpleName();
		methodName = proceeding.getSignature().getName();

		Transaction t = Cat.newTransaction("HmsService", className + "." + methodName);

		try {
			resultObj = proceeding.proceed();
			t.setStatus(Transaction.SUCCESS);
			return resultObj;
		} catch (Exception ex) {
			t.setStatus(ex);
			throw ex;
		} finally {
			t.complete();
		}
	}
}
