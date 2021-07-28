package com.sundaydevblog.springrestapitest.repository;

import com.sundaydevblog.springrestapitest.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-unittest.properties")
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findById_empty(){
        Member member = MemberFixtures.createMember1();

        // exercise
        Optional<Member> actual = memberRepository.findById(3L);
        // verify
        assertThat(actual.isPresent()).isEqualTo(false);
    }

    @Test
    public void findById_hasValue(){
        Member member = MemberFixtures.createMember1();

        // exercise
        Optional<Member> actual = memberRepository.findById(1L);
        // verify
        assertThat(actual.isPresent()).isEqualTo(true);
    }

    @Test
    public void delete_member(){
        Member member1 = MemberFixtures.createMember1();
        Member member2 = MemberFixtures.createMember2();

        // exercise
        memberRepository.delete(member1);
        List<Member> actual = memberRepository.findAll();
        // verify
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0).getId()).isEqualTo(member2.getId().intValue());
        assertThat(actual.get(0).getName()).isEqualTo(member2.getName());
        assertThat(actual.get(0).getEmail()).isEqualTo(member2.getEmail());
    }
}
