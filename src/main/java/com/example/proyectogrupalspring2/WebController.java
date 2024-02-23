package com.example.proyectogrupalspring2;


import com.example.proyectogrupalspring2.actividad.Actividad;
import com.example.proyectogrupalspring2.actividad.ActividadRepository;
import com.example.proyectogrupalspring2.alumno.Alumno;
import com.example.proyectogrupalspring2.alumno.AlumnoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class WebController {
    @Autowired //inicializar los componentes de sprint de forma automatica
    private AlumnoRepository alumnoRepository;
    @Autowired //inicializar los componentes de sprint de forma automatica
    private ActividadRepository actividadRepository;

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "login"; // Devuelve el nombre de la vista, que corresponde al archivo HTML
    }

    @GetMapping("/alumCorrect")
    public String verificarAlumno(@ModelAttribute Alumno alumno,HttpServletRequest request) {
        Boolean existencia=alumnoRepository.existsAlumnoByEmail(alumno.getEmail());

        if(existencia){
            Alumno alumnoBBDD=alumnoRepository.getAlumnoByEmail(alumno.getEmail());
            if(alumnoBBDD.getContrasenya().equals(alumno.getContrasenya())){
                HttpSession session = request.getSession();
                session.setAttribute("alumno", alumnoBBDD);
                return "redirect:/irAlum/" + alumnoBBDD.getIdalumno();
            }else{
                return "redirect:/login";
            }
        }else{
            return "redirect:/login";
        }
    }

    @GetMapping("/irAlum/{id}")
    public String irPaginaAlumno(@PathVariable Long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if(alumnoSession != null){
            Alumno alumno = alumnoSession;
            model.addAttribute("alumno", alumno);
            model.addAttribute("actividades", actividadRepository.getAllByIdAlumno(alumno));
           // System.out.println(actividadRepository.getAllByIdAlumno(alumno));
            return "Pagina_Alumno";
        } else {
            model.addAttribute("alumno", new Alumno());
            return "login"; // Agrega el objeto alumno al modelo para renderizar la vista de login
        }
    }

    //TODO - GET PARA MOSTRAR LAS ACTIVIDADES
    @GetMapping("/actividades")
    public String mostrarActividades(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) {
            Alumno alumno = alumnoSession;
            List<Actividad> actividades = actividadRepository.getAllByIdAlumno(alumno);
            model.addAttribute("alumno", alumno);
            model.addAttribute("actividades", actividades);
            return "Pagina_Alumno";
        } else {
            return "redirect:/login";
        }
    }



    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("alumno", new Alumno());
        return "login";

    }
}
