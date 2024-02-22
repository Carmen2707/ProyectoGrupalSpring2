package com.example.proyectogrupalspring.profesor;

import com.example.proyectogrupalspring.alumno.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    @Query("SELECT (a.nombre, a.apellidos, a.email, a.telefono) FROM Profesor p JOIN p.alumnos a WHERE p.idprofesor = :idprofesor")
    List<Object> getAlumnosByIdprofesor(@Param("idprofesor") Long idprofesor);

}
