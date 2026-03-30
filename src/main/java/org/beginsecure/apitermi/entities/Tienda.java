package org.beginsecure.apitermi.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tiendaAgencia")
public class Tienda {
    @Id
    private String id;
    private String tienda;
    private String agencia;}
