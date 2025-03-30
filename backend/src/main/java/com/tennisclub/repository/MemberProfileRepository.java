package com.tennisclub.repository;

import com.tennisclub.model.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Integer> {
  MemberProfile findByUser_UserId(int userId);
}
