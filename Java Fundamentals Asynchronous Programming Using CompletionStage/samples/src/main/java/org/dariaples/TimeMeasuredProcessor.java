package org.dariaples;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeMeasuredProcessor {

  @Around("@annotation(org.dariaples.TimeMeasured)")
  public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
    long currentTimeMillis = System.currentTimeMillis();
    joinPoint.proceed();
    System.out.println("Execution time of " + joinPoint.getSignature() +
            ": " + ((double)(System.currentTimeMillis() - currentTimeMillis))/1000 +
            " seconds");
    return joinPoint;
  }
}
