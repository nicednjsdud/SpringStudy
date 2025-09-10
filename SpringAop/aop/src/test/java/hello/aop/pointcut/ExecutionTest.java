package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMatch() {
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? public
        // 반환타입    String
        // 선언타입?   hello.aop.member.MemberServiceImpl
        // 메서드명    hello
        // 파라미터    String
        // 예외      생략
    }

    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    hello
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    hel*
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *el*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    *el*
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assert !pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    nono
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.member.MemberServiceImpl
        // 메서드명    hello
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.member.*
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void packageExactFalse() {
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assert !pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.*
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void packageMatchSubPackage1() {
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.member..
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop..
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략
    }
}
