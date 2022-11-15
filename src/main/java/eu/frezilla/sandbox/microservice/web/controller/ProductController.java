package eu.frezilla.sandbox.microservice.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import eu.frezilla.sandbox.microservice.exceptions.ProductNotFoundException;
import eu.frezilla.sandbox.microservice.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import eu.frezilla.sandbox.microservice.dao.ProductRepository;
import eu.frezilla.sandbox.microservice.utils.FilterJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api("API de manipulations des produits")
@RestController
public class ProductController {
    
    @Autowired
    private ProductRepository productDao;
 
    @ApiOperation("Créé ou met à jour un produit")
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
     
    @ApiOperation("Retourne un produit à partir de son identifiant")
    @GetMapping("products/{id}")
    public MappingJacksonValue getProductById(@PathVariable int id) {
        Product product = this.productDao.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException("Le produit #" + id + " est introuvale");
        }
        return FilterJsonUtils.getInstance().filter(product);
    }

    @ApiOperation("Retourne la liste des produits")
    @GetMapping("/products")
    public MappingJacksonValue getProducts() {
        return FilterJsonUtils.getInstance().filter(this.productDao.findAll());
    }
    
    @ApiOperation("Supprime un produit à partir de son identifiant")
    @DeleteMapping("products/{id}")
    public void removeProductById(@PathVariable int id) {
        this.productDao.deleteById(id);
    }

}
