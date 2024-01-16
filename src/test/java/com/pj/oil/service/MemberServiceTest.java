package com.pj.oil.service;

import com.pj.oil.entity.Member;
import com.pj.oil.entity.Role;
import com.pj.oil.entity.UserStatus;
import com.pj.oil.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void init() {
        Member member = Member.builder()
                .id(2L)
                .userId("test1")
                .password("pw")
                .username("name")
                .nickname("nickname1")
                .email("email@email.com")
                .role(Role.USER)
                .issueDate(LocalDate.now())
                .userStatus(UserStatus.NORMAL)
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    public void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @Rollback
    public void 회원가입() throws Exception {
        //given
        Member member = Member.builder()
                .id(2L)
                .userId("test2")
                .password("pw")
                .username("name")
                .nickname("nickname1")
                .email("email@email.com")
                .role(Role.USER)
                .issueDate(LocalDate.now())
                .userStatus(UserStatus.NORMAL)
                .build();
        //when
        Long saveId = memberService.signup(member);

        //then
        assertEquals(member.getUsername(), memberRepository.findById(saveId).get().getUsername());
        System.out.println(memberRepository.findById(saveId).get().toString());
    }

    @Test
    public void 중복회원예외() throws Exception {
        //given
        Member member = Member.builder()
                .id(2L)
                .userId("test1")
                .password("pw")
                .username("name")
                .nickname("nickname1")
                .email("email@email.com")
                .role(Role.USER)
                .issueDate(LocalDate.now())
                .userStatus(UserStatus.NORMAL)
                .build();
        //when
        //then
        assertThrows(IllegalStateException.class, () -> {
            memberService.signup(member);
        });
    }
}
