package eu.frezilla.sandbox.microservice.web.controller;

import eu.frezilla.sandbox.microservice.dao.ShelvingRepository;
import eu.frezilla.sandbox.microservice.exceptions.ShelvingNotFoundException;
import eu.frezilla.sandbox.microservice.model.Shelving;
import eu.frezilla.sandbox.microservice.utils.FilterJsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api("API de manipulations des rayons")
@RestController
public class ShelvingController {
    
    @Autowired private ShelvingRepository shelvingDao;
    
    @ApiOperation("Retourne un rayon à partir de son identifiant")
    @GetMapping("/shelvings/{id}")
    public MappingJacksonValue getShelvingById(@PathVariable int id) {
        Shelving shelving = shelvingDao.findById(id).orElse(null);
        if (shelving == null) {
            throw new ShelvingNotFoundException("Le rayon #" + id + " est introuvable");
        }
        return FilterJsonUtils.getInstance().filter(shelving);
    }
    
    @ApiOperation("Retourne les rayons")
    @GetMapping("/shelvings")
    public MappingJacksonValue getShelvings() {
        return FilterJsonUtils.getInstance().filter(shelvingDao.findAll());
    }
    
}
