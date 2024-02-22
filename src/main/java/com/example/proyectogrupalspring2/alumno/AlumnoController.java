package com.example.proyectogrupalspring.alumno;

import com.example.proyectogrupalspring.SecurityService;
import com.example.proyectogrupalspring.actividad.ActividadRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AlumnoController {
    @Autowired //inicializar los componentes de sprint de forma automatica
    private AlumnoRepository alumnoRepository;



    @GetMapping("/añadirAct")
    public String añadirActividad(@PathVariable Long id, Model model) {
        if (alumnoRepository.existsById(id)) {
            model.addAttribute("alumno", alumnoRepository.findById(id).get());
            return "redirect:/nuevaAct";
        }
        return "redirect:/nuevaAct";
    }

}
