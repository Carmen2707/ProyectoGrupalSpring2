package com.example.proyectogrupalspring.actividad;

import com.example.proyectogrupalspring.SecurityService;
import com.example.proyectogrupalspring.alumno.Alumno;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ActividadController {
    /**

    @PostMapping("/nuevaAct")
    public String nuevaActividad(@ModelAttribute Actividad actividad,HttpServletRequest request,Model model) {

        HttpSession session = request.getSession();
        Alumno alumno = (Alumno) session.getAttribute("alumno");

        if (alumno != null && (!(actividad.getActividad().equals("")) && !(actividad.getFecha().equals(""))) && actividad.getTipo() != null && actividad.getHoras() != null) {

            actividad.setAlumno(alumno);
            actividadRepository.save(actividad);
            return "redirect:/" + actividad.getAlumno().getIdalumno();
        } else {
            return "";
        }
    }**/
}
