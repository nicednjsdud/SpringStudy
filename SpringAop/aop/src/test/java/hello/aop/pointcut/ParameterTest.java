package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import({ParameterTest.ParameterAspect.class})
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        public void allMember() {

        }

        // [logArgs1] String hello.aop.member.MemberServiceImpl.hello(String) arg=helloA
        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1] {} arg={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        // [logArgs2] String hello.aop.member.MemberServiceImpl.hello(String) arg=helloA
        @Around("allMember() && args(arg, ..)") // args()로 특정 파라미터를 지정할 수 있다.
        public Object logArgs2(ProceedingJoinPoint joinPoint, String arg) throws Throwable {
            log.info("[logArgs2] {} arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        // [logArgs3] arg=helloA
        @Before("allMember() && args(arg, ..)") // args()로 특정 파라미터를 지정할 수 있다.
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        // [this]String hello.aop.member.MemberServiceImpl.hello(String), obj=class hello.aop.member.MemberServiceImpl
        @Before("allMember() && this(obj)") // 프록시 객체를 특정 타입으로 지정
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        // [target]String hello.aop.member.MemberServiceImpl.hello(String), obj=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
        @Before("allMember() && target(obj)") // 실제 대상 객체를 특정 타입으로 지정
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        // [@target]String hello.aop.member.MemberServiceImpl.hello(String), annotationValue=@hello.aop.member.annotation.ClassAop()
        @Before("allMember() && @target(annotation)") // 프록시 객체에 특정 애노테이션이 있는지 지정
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{}, annotationValue={}", joinPoint.getSignature(), annotation);
        }

        // [@within]String hello.aop.member.MemberServiceImpl.hello(String),
        @Before("allMember() && @within(annotation)") // 실제 대상 객체에 특정 애노테이션이 있는지 지정
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{}, annotationValue={}", joinPoint.getSignature(), annotation);
        }

        // [@annotation]String hello.aop.member.MemberServiceImpl.hello(String), annotationValue=test value
        @Before("allMember() && @annotation(annotation)") // 메서드에 특정 애노테이션이 있는지 지정
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
        }

    }
}
