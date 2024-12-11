package com.web.investment.controller;

import com.web.investment.dto.ProductPurchaseDTO;
// import com.web.investment.model.ProductPurchaseHistory;
import com.web.investment.service.ProductPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class ProductPurchaseController {

    @Autowired
    private ProductPurchaseService productPurchaseService;

    // Updated to use ProductPurchaseDTO
    @PostMapping("/buy")
    public ResponseEntity<String> purchaseProduct(@RequestBody ProductPurchaseDTO productPurchaseDTO) {
        try {
            // Pass the correct Long type productId to the service
            productPurchaseService.purchaseProduct(productPurchaseDTO.getUserId(), productPurchaseDTO.getProductId());
            return ResponseEntity.ok("Product purchased successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<ProductPurchaseDTO>> getPurchaseHistory(@PathVariable Long userId) {
        List<ProductPurchaseDTO> history = productPurchaseService.getPurchaseHistory(userId);
        return ResponseEntity.ok(history);
    }
    
    @DeleteMapping("/delete/{purchaseId}")
    public ResponseEntity<String> deletePurchaseHistory(@PathVariable Long purchaseId) {
        try {
            productPurchaseService.deletePurchaseHistory(purchaseId);
            return ResponseEntity.ok("Purchase history deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
