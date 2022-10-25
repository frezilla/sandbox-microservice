package eu.frezilla.sandbox.microservice.web.controller;

import eu.frezilla.sandbox.microservice.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import eu.frezilla.sandbox.microservice.dao.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class ProductController {
    
    @Autowired private ProductRepository productDao;
    
    
    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable int id) {
        return productDao.findById(id).orElse(null);
    }
    
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productDao.findAll();
    }
    
    
    
}
