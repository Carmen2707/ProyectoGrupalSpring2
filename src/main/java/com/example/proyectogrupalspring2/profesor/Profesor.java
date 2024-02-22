package com.example.proyectogrupalspring.profesor;

import com.example.proyectogrupalspring.alumno.Alumno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idprofesor;

    private String nombre;
    private String apellidos;
    private String contrasenya;
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "tutor", fetch = FetchType.EAGER)
    private List<Alumno> alumnos = new ArrayList<>();
}
