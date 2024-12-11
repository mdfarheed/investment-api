package com.web.investment.repository;

import com.web.investment.model.ProductPurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPurchaseHistoryRepository extends JpaRepository<ProductPurchaseHistory, Long> {
    List<ProductPurchaseHistory> findByUserId(Long userId);
}



