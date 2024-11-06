package com.iMbank.iMbank.domain.member.repository;

import com.iMbank.iMbank.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT m.name FROM Member m WHERE m.user_id = :id")
    String findNameById(long id);

    @Query("SELECT m FROM Member m WHERE m.user_id = :id")
    Optional<Member> findById(Long id);

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> findByEmail(String email);
}
