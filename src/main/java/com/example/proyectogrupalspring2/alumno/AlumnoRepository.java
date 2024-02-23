package com.example.proyectogrupalspring2.alumno;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Boolean existsAlumnoByEmail(String email);

    Alumno getAlumnoByEmail(String email);
}
