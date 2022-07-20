package kr.co.hellospring;

import kr.co.hellospring.discount.DiscountPolicy;
import kr.co.hellospring.discount.FixDiscountPolicy;
import kr.co.hellospring.discount.RateDiscountPolicy;
import kr.co.hellospring.member.MemberRepository;
import kr.co.hellospring.member.MemberService;
import kr.co.hellospring.member.MemberServiceImpl;
import kr.co.hellospring.member.MemoryMemberRepsitory;
import kr.co.hellospring.order.OrderService;
import kr.co.hellospring.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepsitory();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
