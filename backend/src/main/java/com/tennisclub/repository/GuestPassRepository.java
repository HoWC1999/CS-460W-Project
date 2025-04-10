// src/main/java/com/tennisclub/repository/GuestPassRepository.java
package com.tennisclub.repository;

import com.tennisclub.model.GuestPass;
import com.tennisclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestPassRepository extends JpaRepository<GuestPass, Integer> {
  // Find all guest passes for a given user.
  List<GuestPass> findByUser(User user);


}
