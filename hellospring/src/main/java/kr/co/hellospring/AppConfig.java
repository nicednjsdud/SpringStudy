package kr.co.hellospring;

import kr.co.hellospring.discount.DiscountPolicy;
import kr.co.hellospring.discount.RateDiscountPolicy;
import kr.co.hellospring.member.MemberRepository;
import kr.co.hellospring.member.MemberService;
import kr.co.hellospring.member.MemberServiceImpl;
import kr.co.hellospring.member.MemoryMemberRepository;
import kr.co.hellospring.order.OrderService;
import kr.co.hellospring.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());

    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
