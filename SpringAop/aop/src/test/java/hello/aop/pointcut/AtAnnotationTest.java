package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import({AtAnnotationTest.AtAnnotationAspect.class})
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        // memberService Proxy=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {

        // hello.aop.member.annotation.ClassAop 애노테이션이 있는 대상 지정
        @Pointcut("@annotation(hello.aop.member.annotation.MethodAop)")
        public void atAnnotation() {
        }

        @Around("atAnnotation()")
        public Object doAnnotation(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
            // [@annotation] String hello.aop.member.MemberServiceImpl.hello(String)
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

    }
}
