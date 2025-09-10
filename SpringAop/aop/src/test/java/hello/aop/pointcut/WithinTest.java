package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class WithinTest {

    // within : 특정 타입에만 적용 (정확하게 매칭, 서브타입X), 부모타입 X
    // execution : 특정 타입 + 부모 타입 허용

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointcut.setExpression("within(hello.aop.member.MemberServiceImpl)");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(hello.aop.member.*Service*)");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(hello.aop.member..*)");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);
    }

    @Test
    @DisplayName("타입 매칭 - 부모 타입 허용 X")
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(hello.aop.member.MemberService)");
        assert !pointcut.matches(helloMethod, MemberServiceImpl.class);
    }

    @Test
    @DisplayName("execution 타입 매칭 - 부모 타입 허용 O")
    void executionSuperTypeTrue() {
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        assert pointcut.matches(helloMethod, MemberServiceImpl.class);
    }
}
