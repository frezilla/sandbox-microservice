package eu.frezilla.sandbox.microservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;


@Data
@Entity
public class Product implements Serializable {
    
    @Id
    private int id;
    
    private String name;
    
    private double price;
    
    private double purchasePrice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"products", "hibernateLazyInitializer"})
    private Shelving shelving;

}
