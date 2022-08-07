package kr.co.hellospring.discount;

import kr.co.hellospring.member.Grade;
import kr.co.hellospring.member.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            return price * discountPercent / 100;
        }else{
            return 0;
        }
    }
}
