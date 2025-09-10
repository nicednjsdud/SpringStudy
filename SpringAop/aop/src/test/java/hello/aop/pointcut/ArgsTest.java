package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    // Args
    @Test
    void args() {
        // String 타입 파라미터 허용
        assert pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class);
        // Object 타입 파라미터 허용
        assert pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class);
        // 파라미터가 없어야 함
        assert !pointcut("args()")
                .matches(helloMethod, MemberServiceImpl.class);
        // 모든 타입 허용
        assert pointcut("args(..)")
                .matches(helloMethod, MemberServiceImpl.class);
        // 정확히 하나의 모든 타입 허용
        assert pointcut("args(*)")
                .matches(helloMethod, MemberServiceImpl.class);
        // String 포함 모든 타입 허용
        assert pointcut("args(String, ..)")
                .matches(helloMethod, MemberServiceImpl.class);
    }

    /**
     * Args vs Execution
     * execution(* *(java.io.Serializable)) : 메서드의 시그니처로 판단 (정적)
     * args(java.io.Serializable) : 런타임에 전달된 인수로 판단 (동적)
     */
    @Test
    void argsVsExecution() {
        // Args
        assert pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class);
        assert pointcut("args(java.io.Serializable)")
                .matches(helloMethod, MemberServiceImpl.class);
        assert pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class);

        // Execution
        assert pointcut("execution(* *(String))")
                .matches(helloMethod, MemberServiceImpl.class);
        assert !pointcut("execution(* *(java.io.Serializable))")
                .matches(helloMethod, MemberServiceImpl.class);
        assert !pointcut("execution(* *(Object))")
                .matches(helloMethod, MemberServiceImpl.class);
    }


}
