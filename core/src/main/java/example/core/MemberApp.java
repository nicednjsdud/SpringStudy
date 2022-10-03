package example.core;

import example.core.member.Grade;
import example.core.member.Member;
import example.core.member.MemberService;
import example.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        System.out.println("findMember = " + findMember.getName());
        System.out.println("memberA = " + memberA.getName());
    }
}
