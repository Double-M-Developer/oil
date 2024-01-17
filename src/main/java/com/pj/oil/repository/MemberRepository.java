package com.pj.oil.repository;

import com.pj.oil.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByUserId(String userId);
    Optional<Member> findMemberByUserIdAndAndPassword(String userId, String password);
}
