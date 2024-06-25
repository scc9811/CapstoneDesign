package CapstoneProject.BackEndServer.Aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    
    // ~~BackEndServer.package.class.method
    @Around("execution(* CapstoneProject.BackEndServer.*.*.*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("----- Method {} is starting -----", methodName);

        Object proceed = joinPoint.proceed();

        log.info("----- Method {} is finished -----", methodName);

        return proceed;
    }

}
