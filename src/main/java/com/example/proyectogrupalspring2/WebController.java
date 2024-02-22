package com.example.proyectogrupalspring;

import com.example.proyectogrupalspring.actividad.ActividadRepository;
import com.example.proyectogrupalspring.alumno.Alumno;
import com.example.proyectogrupalspring.alumno.AlumnoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WebController {
    @Autowired //inicializar los componentes de sprint de forma automatica
    private AlumnoRepository alumnoRepository;



    @GetMapping("/login")
    public String getLogin(Model modelo){
        modelo.addAttribute("alumno",new Alumno());
        return "login";
    }
    @Autowired //inicializar los componentes de sprint de forma automatica
    private ActividadRepository actividadRepository;
    @GetMapping("/alumCorrect")
    public String verificarAlumno(@RequestParam String email, @RequestParam String contrasenya, HttpServletRequest request) {
        Alumno alumno1 = alumnoRepository.getAlumnoByEmail(email);
        if (alumno1 != null && alumno1.getContrasenya().equals(contrasenya)) {
            HttpSession session = request.getSession();
            session.setAttribute("alumno", alumno1);
            return "redirect:/" + Long.valueOf(alumno1.getIdalumno());
        } else {
            return "Usuario o contraseña incorrecto";
        }
    }
    @GetMapping("/{id}")
    public String irPaginaAlumno(@PathVariable Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) {
            Alumno alumno = alumnoSession;
            model.addAttribute("actividades", actividadRepository.getAllByIdAlumno(alumno));
            System.out.println(actividadRepository.getAllByIdAlumno(alumno));
            return "actividades";
        }
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request)  {
        HttpSession session=request.getSession();
        session.invalidate();
        model.addAttribute("alumno",new Alumno());
        return "login";

    }
}
