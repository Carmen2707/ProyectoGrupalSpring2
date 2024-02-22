package com.example.proyectogrupalspring.alumno;

import com.example.proyectogrupalspring.actividad.Actividad;
import com.example.proyectogrupalspring.empresa.Empresa;
import com.example.proyectogrupalspring.profesor.Profesor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idalumno;
    private String dni;
    private String nombre;
    private String apellidos;
    private String contrasenya;
    private String email;

    @ManyToOne
    @JoinColumn(name = "empresa", referencedColumnName = "idempresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "tutor", referencedColumnName = "idprofesor")
    private Profesor tutor;

    private String observaciones;
    private Date nacimiento;
    private Integer horasdual;
    private Integer horasfct;
    private Integer telefono;

    @JsonIgnore
    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    private List<Actividad> actividad_diaria = new ArrayList<>();

}
