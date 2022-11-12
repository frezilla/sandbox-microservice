package eu.frezilla.sandbox.microservice.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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
import org.springframework.http.converter.json.MappingJacksonValue;
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
    public MappingJacksonValue getProducts() {
        List<Product> products = productDao.findAll();
        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("purchasePrice");
        FilterProvider filters = new SimpleFilterProvider().addFilter("productFilter", myFilter);
        MappingJacksonValue mjv = new MappingJacksonValue(products);
        mjv.setFilters(filters);
        return mjv;
    }

}
