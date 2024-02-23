package com.example.proyectogrupalspring2.actividad;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ActividadController {
    /**

     @PostMapping("/nuevaAct") public String nuevaActividad(@ModelAttribute Actividad actividad,HttpServletRequest request,Model model) {

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
