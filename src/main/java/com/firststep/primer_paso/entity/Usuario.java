package com.firststep.primer_paso.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // le dice a JPA que esta clase es una tabla en Postgres
@Table(name = "usuarios") // nombre de la tabla en la base de datos
@Data // lombok: genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // lombok: genera constructor vacío que JPA necesita obligatoriamente
@AllArgsConstructor // lombok: genera constructor con todos los campos
public class Usuario {

    @Id // indica que este campo es la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // el id lo genera Postgres automáticamente (autoincrement)
    private Long id;

    @Column(nullable = false, unique = true) // no puede ser nulo y debe ser único en la tabla
    private String email;

    @Column(nullable = false)
    private String password; // aquí guardaremos la contraseña encriptada con BCrypt

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING) // guarda el enum como texto en la BD, no como número
    private Rol rol; // PASAJERO o CONDUCTOR
}
