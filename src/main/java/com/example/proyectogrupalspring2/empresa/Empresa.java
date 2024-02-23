package com.example.proyectogrupalspring2.empresa;


import com.example.proyectogrupalspring2.alumno.Alumno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "empresas")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idempresa;
    private String nombre;
    private String email;
    private Integer telefono;
    private String responsable;
    private String observaciones;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER)
    private List<Alumno> alumnos = new ArrayList<>();

    @Override
    public String toString() {
        return "Empresa{" +
                "idempresa=" + idempresa +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono=" + telefono +
                ", responsable='" + responsable + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", alumnos=" + alumnos.stream().map(Alumno::getIdalumno).collect(Collectors.toList()) +
                '}';
    }

}
