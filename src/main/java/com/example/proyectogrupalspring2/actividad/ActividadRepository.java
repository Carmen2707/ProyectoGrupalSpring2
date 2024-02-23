package com.example.proyectogrupalspring2.actividad;


import com.example.proyectogrupalspring2.alumno.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    @Query("SELECT a FROM Actividad a where a.alumno=:alumno")
    List<Actividad> getAllByIdAlumno(@Param("alumno") Alumno alumno);
}
