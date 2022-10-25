package eu.frezilla.sandbox.microservice.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Product implements Serializable {
    
    @Id
    private int id;
    
    private String name;
    
    private double price;

}
