package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} args={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try {}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                log.info("[retry] {}/{} failed: {}", retryCount, maxRetry, e.getMessage());
            }
        }
        throw new IllegalStateException("max retry count exceeded");
    }
}
