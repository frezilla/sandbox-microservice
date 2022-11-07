package eu.frezilla.sandbox.microservice.dao;

import eu.frezilla.sandbox.microservice.model.Shelving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelvingRepository extends JpaRepository<Shelving, Integer> {
    
}
