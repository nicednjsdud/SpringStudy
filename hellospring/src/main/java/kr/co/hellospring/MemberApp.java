package kr.co.hellospring;

import kr.co.hellospring.member.Grade;
import kr.co.hellospring.member.Member;
import kr.co.hellospring.member.MemberService;
import kr.co.hellospring.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findmember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findmember.getName());
    }
}
