package org.beginsecure.apitermi.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios") // La colección que crearás en MongoAtlas
public class Usuario {

    @Id
    private String id;

    private String username;

    // Aquí se guardará la contraseña ya encriptada (el churrete de texto BCrypt)
    private String password;

    // Aquí guardaremos el rol (ej: "ROLE_ADMIN").
    // Los roles en Spring Security suelen empezar por la palabra "ROLE_"
    private String roles;
}