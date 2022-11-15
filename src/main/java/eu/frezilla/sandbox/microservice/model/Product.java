package eu.frezilla.sandbox.microservice.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@JsonFilter("productFilter")
public class Product implements Serializable {
    
    @Id
    @GeneratedValue
    private int id;
    
    @Size(min = 3, max = 255)
    private String name;
    
    @Min(value = 1)
    private double price;
    
    @Min(value = 1)
    private double purchasePrice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"products", "hibernateLazyInitializer"})
    private Shelving shelving;

}
