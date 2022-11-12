package eu.frezilla.sandbox.microservice.web.controller;

import eu.frezilla.sandbox.microservice.exceptions.ProductNotFoundException;
import eu.frezilla.sandbox.microservice.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import eu.frezilla.sandbox.microservice.dao.ProductRepository;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productDao;
 
    @PostMapping(value = "/products")
     public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
         Product productSaved = productDao.save(product);
         if (Objects.isNull(productSaved)) {
             return ResponseEntity.noContent().build();
         }
         URI location = ServletUriComponentsBuilder
                 .fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(productSaved.getId())
                 .toUri();
         return ResponseEntity.created(location).build();
     }
    
    @GetMapping("products/{id}")
    public Product getProductById(@PathVariable int id) {
        Product product = productDao.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException("Le produit #" + id + " est introuvale");
        } 
        return product;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productDao.findAll();
    }

}
