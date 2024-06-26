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

    /*
     * proceed 반환이 없으면 메소드의 반환값이 전달되지 않음.
     * 모든 메소드의 실행 순서
     * 1. logExecution
     * 2. logExecution 에서 requestMethod
     * 3. requestMethod 의 반환값이 logExecution 으로 전달.
     * 4. logExecution 에서 다시 반환.
     *
     * execution 경로 설명 : ~~BackEndServer.package.class.method(..)
     */
    @Around("execution(* CapstoneProject.BackEndServer.*.*.*(..))")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("----- Method '{}' is starting -----", methodName);

        Object proceed = joinPoint.proceed();

        log.info("----- Method '{}' is finished -----", methodName);


        return proceed;
    }

}
