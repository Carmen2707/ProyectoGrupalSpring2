package com.example.proyectogrupalspring2.actividad;


import com.example.proyectogrupalspring2.alumno.Alumno;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "actividad")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idactividad;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private String tipo;
    private Integer horas;
    private String actividad;
    private String observacion;
    @ManyToOne
    @JoinColumn(name = "alumno", referencedColumnName = "idalumno")
    private Alumno alumno;


}
