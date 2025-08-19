package com.backend.leavemanagementsystem.repository;

import com.backend.leavemanagementsystem.entity.HrUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrRepository extends JpaRepository<HrUser, Long> {

    Optional<HrUser> findByUsername(String username);
}
