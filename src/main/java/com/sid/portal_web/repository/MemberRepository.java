package com.sid.portal_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sid.portal_web.entity.News.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}