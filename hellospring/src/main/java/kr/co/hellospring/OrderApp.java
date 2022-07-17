package kr.co.hellospring;

import kr.co.hellospring.member.Grade;
import kr.co.hellospring.member.Member;
import kr.co.hellospring.member.MemberService;
import kr.co.hellospring.member.MemberServiceImpl;
import kr.co.hellospring.order.Order;
import kr.co.hellospring.order.OrderService;
import kr.co.hellospring.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}