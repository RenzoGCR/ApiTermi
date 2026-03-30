package org.beginsecure.apitermi.repositories;

import org.beginsecure.apitermi.entities.Tienda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiendaRepository extends MongoRepository<Tienda, String> {
    Optional<Tienda> findByTienda(String tienda);
    List<Tienda> findByAgencia(String agencia);
    void deleteByTienda(String tienda);
    void deleteByAgencia(String agencia);
    boolean existsByTienda(String tienda);
    boolean existsByAgencia(String agencia);
    Page<Tienda> findByAgencia(String agencia, Pageable pageable);
}
