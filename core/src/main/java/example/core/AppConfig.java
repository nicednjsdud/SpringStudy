package example.core;

import example.core.discount.DiscountPolicy;
import example.core.discount.FixDiscountPolicy;
import example.core.member.MemberService;
import example.core.member.MemberServiceImpl;
import example.core.member.MemoryMemberRepository;
import example.core.order.OrderService;
import example.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }


}
