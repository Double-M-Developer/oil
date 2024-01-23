package com.pj.oil.service;

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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        //로그인용
        Member member = Member.builder()
                .id(1L)
                .userId("test1")
                .password("pw")
                .username("name")
                .nickname("nickname1")
                .email("email@email.com")
                .role(Role.USER)
                .issueDate(LocalDate.now())
                .userStatus(UserStatus.NORMAL)
                .build();
        //수정용
        Member member2 = Member.builder()
                .id(2L)
                .userId("test2")
                .password("pw")
                .username("name2")
                .nickname("nickname2")
                .email("email@email.com")
                .role(Role.USER)
                .issueDate(LocalDate.now())
                .userStatus(UserStatus.NORMAL)
                .build();
        memberRepository.save(member);
        memberRepository.save(member2);
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
                .id(3L)
                .userId("test3")
                .password("pw")
                .username("name")
                .nickname("nickname3")
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
                .userId("test2")
                .password("pw")
                .username("name")
                .nickname("nickname2")
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

    @Test
    public void 회원id로_조회() throws Exception {
        //given
        //when
        Optional<Member> member = memberService.findById(1L);
        //then
        member.ifPresent(value -> assertEquals("test1", value.getUserId()));
    }

    @Test
    public void 회원전체조회() throws Exception {
        //given
        //when
        List<Member> member = memberService.findAllMembers();
        //then
        assertFalse(member.isEmpty());
    }

    @Test
    public void 로그인() throws Exception {
        LoginRequestDto dto = LoginRequestDto.builder()
                .userId("test1")
                .password("pw")
                .build();
        Long loginMemberId = memberService.login(dto);

        Optional<Member> member = memberService.findById(loginMemberId);
        //then
        assertFalse(member.isEmpty());
    }

    @Test
    public void 사용자수정() throws Exception {
        String userId = "test2";
        MemberUpdateFormDto dto = MemberUpdateFormDto.builder()
                .nickname("new")
                .password("newpw")
                .build();
        Long updateMemberId = memberService.updateMember(userId, dto);

        Optional<Member> member = memberService.findById(updateMemberId);
        System.out.println(member.toString());
        //then
        assertEquals(dto.getNickname(), member.get().getNickname());
    }
}
