// SupplierService.java
package org.example.grocerystore.service;

import org.example.grocerystore.model.entity.Supplier;
import org.example.grocerystore.model.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository repo;
    public SupplierService(SupplierRepository r){ this.repo=r; }
    public List<Supplier> getAllSuppliers(){ return repo.findAll(); }
    public Supplier add(Supplier s){ return repo.save(s); }
    public Supplier updateSupplier(Supplier s){ return repo.save(s); }
    public void deleteSupplier(Long id){
        repo.deleteProductSupplierLinks(id);
        repo.deleteById(id);
    }
}
