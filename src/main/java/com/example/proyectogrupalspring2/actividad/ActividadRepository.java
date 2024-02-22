package com.example.proyectogrupalspring.actividad;

import com.example.proyectogrupalspring.alumno.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    @Query("SELECT a FROM Actividad a where a.alumno=:alumno")
    List<Actividad> getAllByIdAlumno(@Param("alumno") Alumno alumno);
}
