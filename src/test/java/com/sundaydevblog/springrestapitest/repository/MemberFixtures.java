package com.sundaydevblog.springrestapitest.repository;

import com.sundaydevblog.springrestapitest.entity.Member;

public class MemberFixtures {
    public static Member createMember1() {
        Member member = new Member();
        member.setId(1L);
        member.setName("huynhctd");
        member.setEmail("huynhctd@gmail.com");
        return member;
    }

    public static Member createMember2() {
        Member member = new Member();
        member.setId(2L);
        member.setName("hungpk");
        member.setEmail("hungpk@gmail.com");
        return member;
    }
}
