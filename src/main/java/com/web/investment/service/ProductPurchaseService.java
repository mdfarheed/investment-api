package com.web.investment.service;

import com.web.investment.dto.ProductPurchaseDTO;
import com.web.investment.model.Product;
import com.web.investment.model.ProductPurchaseHistory;
import com.web.investment.model.TransactionHistory;
import com.web.investment.model.Wallet;
import com.web.investment.model.User;
import com.web.investment.repository.ProductPurchaseHistoryRepository;
import com.web.investment.repository.ProductRepository;
import com.web.investment.repository.TransactionHistoryRepository;
import com.web.investment.repository.WalletRepository;
import com.web.investment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
// import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductPurchaseService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ProductPurchaseHistoryRepository productPurchaseHistoryRepository;
    

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    // Method to purchase product and track countdown
    public void purchaseProduct(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        double productPrice = product.getPrice();

        if (wallet.getBalance() < productPrice) {
            throw new RuntimeException("Insufficient funds");
        }

        wallet.setBalance(wallet.getBalance() - productPrice);
        walletRepository.save(wallet); // Save updated wallet balance

        // Calculate the countdown (time set by admin in product)

        // Create and save Product Purchase History
        ProductPurchaseHistory productPurchaseHistory = new ProductPurchaseHistory();
        productPurchaseHistory.setUser(user);
        productPurchaseHistory.setProduct(product);
        productPurchaseHistory.setPrice(productPrice); // Set product price
        productPurchaseHistory.setPurchaseTime(Instant.now()); // Set the current purchase time
        productPurchaseHistory.setDuration(product.getTime()); // Set the countdown (in seconds)
        productPurchaseHistory.setProductName(product.getName());
        productPurchaseHistoryRepository.save(productPurchaseHistory); // Save purchase history

        // Create and save Transaction History for the purchase
        TransactionHistory transaction = new TransactionHistory();
        transaction.setUser(user);
        transaction.setType(TransactionHistory.TransactionType.PURCHASE); // Set the transaction type
        transaction.setAmount(productPrice); // Set the amount spent on the purchase
        transaction.setTimestamp(Instant.now()); // Set the current timestamp
        transactionHistoryRepository.save(transaction); // Save the transaction in history
    }
    // Fetch purchase history for the user
    public List<ProductPurchaseDTO> getPurchaseHistory(Long userId) {
        List<ProductPurchaseHistory> histories = productPurchaseHistoryRepository.findByUserId(userId);
        return histories.stream()
                .map(history -> {
                    ProductPurchaseDTO dto = new ProductPurchaseDTO(userId, userId, userId, userId, null, 0, null);
                    dto.setId(history.getId());
                    dto.setUserId(history.getUser().getId());
                    dto.setProductId(history.getProduct().getId());
                    dto.setPrice(history.getPrice());
                    dto.setPurchaseTime(history.getPurchaseTime());
                    dto.setDuration(history.getDuration());
                    dto.setProductName(history.getProductName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void deletePurchaseHistory(Long purchaseId) {
        ProductPurchaseHistory history = productPurchaseHistoryRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase history not found"));
        productPurchaseHistoryRepository.delete(history);
    }
}
