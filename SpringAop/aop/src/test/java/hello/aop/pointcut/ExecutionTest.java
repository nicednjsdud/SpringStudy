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

    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.member.MemberServiceImpl
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략
    }

    @Test
    void typeMatchSuperType() {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.member.MemberService (MemberServiceImpl의 부모 타입)
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략

        // 부모 타입을 선언해도 자식 타입인 MemberServiceImpl의 hello 메서드가 매칭된다.
        // 즉, 선언된 타입에 있는 메서드만 매칭된다.
        // 부모 타입에 선언된 메서드가 자식 타입에 오버라이딩 되어있지 않으면 매칭되지 않는다.
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assert !pointcut.matches(internalMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.member.MemberService (MemberServiceImpl의 부모 타입)
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략

        // internal 메서드는 MemberService 인터페이스에 선언되어 있지 않기 때문에 매칭되지 않는다.
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assert !pointcut.matches(internalMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   hello.aop.member.MemberServiceImpl
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략

        // MemberServiceImpl에 선언된 모든 메서드가 매칭되지 않는다.
    }

    // String 타입 파라미터 허용
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    *
        // 파라미터    (String)
        // 예외      생략

        // String 타입의 파라미터 허용
    }

    // 파라미터 없음
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        assert !pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    *
        // 파라미터    ()
        // 예외      생략

        // 파라미터 없음
    }

    // 정확히 하나의 파라미터, 모든 타입 허용
    @Test
    void argsMatchStar() {
        pointcut.setExpression("execution(* *(*))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    *
        // 파라미터    (*)
        // 예외      생략

        // 정확히 하나의 파라미터, 모든 타입 허용
    }

    // 숫자 상관없이 모든 파라미터, 모든 타입 허용
    // (), (Xxx), (Xxx, Yyy) 등 모든 파라미터 허용
    @Test
    void argsMatchAll() {
        pointcut.setExpression("execution(* *(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    *
        // 파라미터    (..)
        // 예외      생략

        // 숫자 상관없이 모든 파라미터, 모든 타입 허용
        // (), (Xxx), (Xxx, Yyy) 등 모든 파라미터 허용
    }

    // String 타입으로 시작, 숫자 상관없이 모든 파라미터, 모든 타입 허용
    @Test
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String, ..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);

        // 매칭 조건
        // 접근제어자? 생략
        // 반환타입    *
        // 선언타입?   생략
        // 메서드명    *
        // 파라미터    (String, ..)
        // 예외      생략

        // String 타입으로 시작, 숫자 상관없이 모든 파라미터, 모든 타입 허용
        // (String, Xxx), (String, Xxx, Yyy) 등 모든 파라미터 허용
    }


}
