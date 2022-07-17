package kr.co.hellospring.order;

import kr.co.hellospring.discount.DiscountPolicy;
import kr.co.hellospring.discount.FixDiscountPolicy;
import kr.co.hellospring.discount.RateDiscountPolicy;
import kr.co.hellospring.member.Member;
import kr.co.hellospring.member.MemberRepository;
import kr.co.hellospring.member.MemoryMemberRepsitory;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepsitory();
    private DiscountPolicy discountPolicy;
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
