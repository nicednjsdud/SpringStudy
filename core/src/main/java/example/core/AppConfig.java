package example.core;

import example.core.discount.FixDiscountPolicy;
import example.core.member.MemberService;
import example.core.member.MemberServiceImpl;
import example.core.member.MemoryMemberRepository;
import example.core.order.OrderService;
import example.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }


}
