package com.example.proyectogrupalspring.actividad;


import com.example.proyectogrupalspring.alumno.Alumno;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idactividad;
    private Date fecha;
    private String tipo;
    private Integer horas;
    private String actividad;
    private String observacion;
    @ManyToOne
    @JoinColumn(name = "alumno", referencedColumnName = "idalumno")
    private Alumno alumno;
}
