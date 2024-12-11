package com.web.investment.repository;

import com.web.investment.model.BankInfo;
// import com.web.investment.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

@Repository
public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
    BankInfo findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}

