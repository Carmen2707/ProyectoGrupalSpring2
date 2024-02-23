package com.example.proyectogrupalspring2.alumno;

import com.example.proyectogrupalspring2.actividad.Actividad;
import com.example.proyectogrupalspring2.empresa.Empresa;
import com.example.proyectogrupalspring2.profesor.Profesor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "Alumno{" +
                "idalumno=" + idalumno +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", contrasenya='" + contrasenya + '\'' +
                ", email='" + email + '\'' +
                ", empresa=" + (empresa != null ? empresa.getIdempresa() : null) +
                ", tutor=" + (tutor != null ? tutor.getIdprofesor() : null) +
                ", observaciones='" + observaciones + '\'' +
                ", nacimiento=" + nacimiento +
                ", horasdual=" + horasdual +
                ", horasfct=" + horasfct +
                ", telefono=" + telefono +
                ", actividad_diaria=" + actividad_diaria.stream().map(Actividad::getIdactividad).collect(Collectors.toList()) +
                '}';
    }

}
