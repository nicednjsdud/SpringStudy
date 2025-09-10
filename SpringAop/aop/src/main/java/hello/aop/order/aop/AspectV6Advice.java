package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

//    @Aspect
//    @Order(2)
//    public static class LogAspect {
//        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
//        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
//            log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
//            return joinPoint.proceed();
//        }
//    }

//    @Aspect
//    @Order(1) //숫자가 작을 수록 먼저 실행됨
//    public static class TxAspect {
//        //hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
//        @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
//        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//
//            try {
//                //@Before
//                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//                Object result = joinPoint.proceed();
//                //@AfterReturning
//                log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
//                return result;
//            } catch (Exception e) {
//                //@AfterThrowing
//                log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//                throw e;
//            } finally {
//                //@After
//                log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
//            }
//        }
//    }


    // @Before advice
    // 본문 실행 이전에 실행
    // before 블록과 유사
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    // @After advice
    // 예외 발생 여부와 상관없이 무조건 실행
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    // @After advice
    // 예외 발생 여부와 상관없이 무조건 실행
    // String 타입으로 반환값 제한
    // Integer 타입 반환 불가
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doReturn2(JoinPoint joinPoint, String result) {
        log.info("[return2] {} return={}", joinPoint.getSignature(), result);
    }


    // @AfterThrowing advice
    // 예외가 발생했을 때만 실행
    // catch 블록과 유사
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[throwing] {} ex={}", joinPoint.getSignature(), ex);
    }

    // @After advice
    // 예외 발생 여부와 상관없이 무조건 실행
    // finally 블록과 유사
    @After("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
