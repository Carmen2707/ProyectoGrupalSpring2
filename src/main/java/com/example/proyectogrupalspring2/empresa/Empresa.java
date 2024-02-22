package com.example.proyectogrupalspring.empresa;

import com.example.proyectogrupalspring.alumno.Alumno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idempresa ;
    private String nombre;
    private String email;
    private Integer telefono;
    private String responsable;
    private String observaciones;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa",fetch = FetchType.EAGER)
    private List<Alumno> alumnos = new ArrayList<>();


}
