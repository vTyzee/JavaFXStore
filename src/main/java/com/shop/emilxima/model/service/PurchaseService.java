package com.shop.emilxima.model.service;

import com.shop.emilxima.model.entity.Customer;
import com.shop.emilxima.model.entity.Product;
import com.shop.emilxima.model.entity.Purchase;
import com.shop.emilxima.model.repository.CustomerRepository;
import com.shop.emilxima.model.repository.ProductRepository;
import com.shop.emilxima.model.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    public PurchaseService(PurchaseRepository purchaseRepo,
                           CustomerRepository customerRepo,
                           ProductRepository productRepo) {
        this.purchaseRepo = purchaseRepo;
        this.customerRepo = customerRepo;
        this.productRepo  = productRepo;
    }

    /**
     * Пример покупки: списать balance у customer,
     * уменьшить quantity у product,
     * создать запись Purchase.
     */
    public void purchaseProduct(Long customerId, Long productId, int qty) {
        Customer c = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("No such customer"));
        Product p = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("No such product"));

        double totalPrice = p.getPrice() * qty;
        if (c.getBalance() < totalPrice) {
            throw new RuntimeException("Not enough balance");
        }
        if (p.getQuantity() < qty) {
            throw new RuntimeException("Not enough products");
        }

        // Обновляем
        c.setBalance(c.getBalance() - totalPrice);
        p.setQuantity(p.getQuantity() - qty);
        customerRepo.save(c);
        productRepo.save(p);

        // Сохраняем запись о покупке
        Purchase purchase = new Purchase(c, p, qty, LocalDateTime.now());
        purchaseRepo.save(purchase);
    }
}
