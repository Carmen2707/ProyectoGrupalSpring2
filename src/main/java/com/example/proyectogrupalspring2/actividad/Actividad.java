package com.example.proyectogrupalspring2.actividad;


import com.example.proyectogrupalspring2.alumno.Alumno;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public String getFechaString() {
        // Convierte la fecha a una cadena en el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.fecha);
    }

    public void setFechaString(String fechaString) {
        // Convierte la cadena de fecha al objeto Date y establece la fecha
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.fecha = dateFormat.parse(fechaString);
        } catch (ParseException e) {
            // Manejar cualquier error de parseo de fecha aquí
            e.printStackTrace();
        }
    }
}
