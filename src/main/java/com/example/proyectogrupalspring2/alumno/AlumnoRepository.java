package com.example.proyectogrupalspring.alumno;

import com.example.proyectogrupalspring.alumno.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository  extends JpaRepository<Alumno, Long> {
    Boolean existsAlumnoByEmail(String email);
    Alumno getAlumnoByEmail(String email);
}
