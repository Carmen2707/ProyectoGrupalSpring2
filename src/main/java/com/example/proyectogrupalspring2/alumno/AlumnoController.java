package com.example.proyectogrupalspring2.alumno;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
