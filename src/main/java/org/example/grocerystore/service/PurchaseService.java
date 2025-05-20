// PurchaseService.java
package org.example.grocerystore.service;

import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.model.entity.Purchase;
import org.example.grocerystore.model.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository repo;
    private final ProductService productService;
    private final CustomerService customerService;

    public PurchaseService(PurchaseRepository r,ProductService ps,CustomerService cs){
        this.repo=r; this.productService=ps; this.customerService=cs;
    }

    public String buyProduct(Long customerId,Long productId,int qty){
        var cust = customerService.findById(customerId).orElse(null);
        var prod = productService.findById(productId).orElse(null);
        if(cust==null) return "Покупатель не найден!";
        if(prod==null) return "Продукт не найден!";
        if(prod.getStock()<qty) return "Недостаточно на складе!";
        double total = prod.getPrice()*qty;
        if(cust.getBalance()<total) return "Недостаточно средств!";
        cust.setBalance(cust.getBalance()-total);
        prod.setStock(prod.getStock()-qty);
        customerService.update(cust);
        productService.update(prod);
        repo.save(new Purchase(prod,cust,qty,total,LocalDateTime.now()));
        return "Покупка выполнена!";
    }

    public double getIncome(LocalDateTime s,LocalDateTime e){
        var sum = repo.getIncomeBetween(s,e);
        return sum==null?0:sum;
    }
    public List<Object[]> getTopProductBetween(LocalDateTime s,LocalDateTime e){
        return repo.getTopProductBetween(s,e);
    }
    public List<Object[]> getTopProductAllTime(){
        return repo.getTopProductAllTime();
    }
}
