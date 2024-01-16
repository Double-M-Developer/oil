package com.pj.oil.service;

import com.pj.oil.dto.LoginRequestDto;
import com.pj.oil.dto.MemberUpdateFormDto;
import com.pj.oil.entity.Member;
import com.pj.oil.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    public List<Member> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        LOGGER.info("[findAllMembers] response member size: {}", members.size());
        return members;
    }

    public Long signup(Member member) {
        LOGGER.info("[signup] request member: {}", member.toString());
        validateDuplicateMember(member); // 중복 회원 검증
        Member signupMember = memberRepository.save(member);
        LOGGER.info("[signup] response member: {}", signupMember.toString());
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        LOGGER.info("[validateDuplicateMember] request member: {}", member.toString());
        Optional<Member> findMembers = memberRepository.findMemberByUserId(member.getUserId());
        if (findMembers.isPresent()) {
            LOGGER.info("[validateDuplicateMember] member data dose existed : {}", member.toString());
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } else {
            LOGGER.info("[validateDuplicateMember] member data dose not existed");
        }
    }

    public Optional<Member> findById(Long id) {
        Optional<Member> findMembers = memberRepository.findById(id);
        if (findMembers.isPresent()) {
            LOGGER.info("[findById] member data dose existed : {}", findMembers.toString());
        }
        LOGGER.info("[findById] member data dose not existed, id: {}", id);
        return findMembers;
    }

    public Long login(LoginRequestDto dto) {
        LOGGER.info("[login] login data: {}", dto.toString());
        String userId = dto.getUserId();
        if(memberRepository.findMemberByUserId(userId).isEmpty()) {
            LOGGER.info("[login] userId data dose not existed");
            //예외처리 구문
        }
        String password = dto.getPassword();
        if(memberRepository.findMemberByUserIdAndAndPassword(userId, password).isEmpty()){
            LOGGER.info("[login] password data dose not matched");
            // 예외처리 구문
        }
        Long loginMemberId = memberRepository.findMemberByUserIdAndAndPassword(userId, password).get().getId();
        LOGGER.info("[login] member data dose exited, login member id: {}", loginMemberId);
        return loginMemberId;
    }

    public Long updateMember(String userId, MemberUpdateFormDto dto) {
        LOGGER.info("[updateMember] update data: {}", dto.toString());
        Optional<Member> memberByUserId = memberRepository.findMemberByUserId(userId);
        if(memberByUserId.isEmpty()) {
            LOGGER.info("[updateMember] userId data dose not existed");
            //예외처리 구문
        }
        LOGGER.info("[updateMember] member data dose existed, member: {}", memberByUserId);
        Long updateRequestMemberId = memberByUserId.get().getId();
        LOGGER.info("[updateMember] member data dose existed, member id: {}", updateRequestMemberId);

        Member updateMember = memberByUserId.get();
        updateMember.updateNickname(dto.getNickname());
        updateMember.updatePassword(dto.getPassword());
        return updateMember.getId();
    }
}
