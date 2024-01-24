package com.pj.oil.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessTokenRepository extends JpaRepository<Token, Long> {

    @Query("select t " +
            "from Token t " +
            "inner join Member m " +
            "on t.member.id = m.id " +
            "where m.id = :memberId " +
            "and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByMember(Long memberId);

    Optional<Token> findByToken(String token);
}
