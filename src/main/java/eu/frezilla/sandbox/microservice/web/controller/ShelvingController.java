package eu.frezilla.sandbox.microservice.web.controller;

import eu.frezilla.sandbox.microservice.dao.ShelvingRepository;
import eu.frezilla.sandbox.microservice.exceptions.ShelvingNotFoundException;
import eu.frezilla.sandbox.microservice.model.Shelving;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShelvingController {
    
    @Autowired private ShelvingRepository shelvingDao;
    
    @GetMapping("/shelvings/{id}")
    public Shelving getShelvingById(@PathVariable int id) {
        Shelving shelving = shelvingDao.findById(id).orElse(null);
        if (shelving == null) {
            throw new ShelvingNotFoundException("Le rayon #" + id + " est introuvable");
        }
        return shelving;
    }
    
    @GetMapping("/shelvings")
    public List<Shelving> getShelvings() {
        return shelvingDao.findAll();
    }
    
}
