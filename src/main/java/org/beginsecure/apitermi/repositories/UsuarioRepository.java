package org.beginsecure.apitermi.repositories;

import org.beginsecure.apitermi.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    // Spring Data MongoDB creará automáticamente la consulta para buscar por el campo "username"
    Optional<Usuario> findByUsername(String username);
}