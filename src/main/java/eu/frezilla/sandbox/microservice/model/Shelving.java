package eu.frezilla.sandbox.microservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class Shelving implements Serializable {
    
    @Id
    @JsonIgnore
    private int id;
    
    private String name;
    
    @OneToMany(
            mappedBy = "shelving",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("shelving")
    private List<Product> products;
    
    public void addProduct(@NonNull Product product) {
        products.add(product);
        product.setShelving(this);
    }
    
    public void removeProduct(@NonNull Product product) {
        products.remove(product);
        product.setShelving(null);
    }
    
}
