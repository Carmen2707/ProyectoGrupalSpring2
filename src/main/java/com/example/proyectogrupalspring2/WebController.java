package com.example.proyectogrupalspring2;


import com.example.proyectogrupalspring2.actividad.Actividad;
import com.example.proyectogrupalspring2.actividad.ActividadRepository;
import com.example.proyectogrupalspring2.alumno.Alumno;
import com.example.proyectogrupalspring2.alumno.AlumnoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class WebController {
    @Autowired //inicializar los componentes de sprint de forma automatica
    private AlumnoRepository alumnoRepository;

    @Autowired //inicializar los componentes de sprint de forma automatica
    private ActividadRepository actividadRepository;

    @Autowired //inicializar los componentes de sprint de forma automatica
    private SecurityService securityService;



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

    @GetMapping("/irAlum/{idAlumno}")
    public String irPaginaAlumno(@PathVariable Long idAlumno, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if(alumnoSession != null){
            Alumno alumno = alumnoSession;
            model.addAttribute("id", idAlumno);
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
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    //POST PARA AÑADIR ACTIVIDAD
    @PostMapping("/nuevaAct/{idAlumno}")
    public String nuevaActividadPost(@PathVariable Long idAlumno,@RequestParam String fecha, @RequestParam Integer horas, @RequestParam String tipo, @RequestParam String actividad,
                                     @RequestParam String observacion, HttpServletRequest request, Model model) {

        // Obtiene la sesión actual del usuario
        HttpSession session = request.getSession();
        // Verifica si hay un alumno en sesión
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) { // Si hay un alumno en sesión
            // Verifica que los datos de la actividad no estén vacíos
            if (fecha != null && !fecha.isEmpty() && tipo != null && !tipo.isEmpty() && actividad != null && !actividad.isEmpty() && observacion != null) {

                // Crea un formato de fecha para convertir la cadena de fecha en un objeto Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaDate;
                try {
                    // Convierte la cadena de fecha en un objeto Date
                    fechaDate = dateFormat.parse(fecha);
                } catch (ParseException e) {
                    // Maneja la excepción en caso de error al analizar la fecha
                    e.printStackTrace();
                    // Redirige de vuelta a la página de añadir actividad si hay un error en la fecha
                    return "redirect:/irAña/" + idAlumno;
                }

                // Crea un nuevo objeto de actividad y establece sus atributos
                Actividad nuevaActividad = new Actividad();
                nuevaActividad.setFecha(fechaDate);
                nuevaActividad.setHoras(horas);
                nuevaActividad.setTipo(tipo);
                nuevaActividad.setActividad(actividad);
                nuevaActividad.setObservacion(observacion);
                nuevaActividad.setAlumno(alumnoSession);

                // Guarda la nueva actividad en la base de datos
                actividadRepository.save(nuevaActividad);

                // Redirige a la página del alumno después de guardar la actividad
                return "redirect:/irAlum/" + idAlumno;
            } else {
                // Redirige de vuelta a la página de añadir actividad si faltan datos
                return "redirect:/irAña/" + idAlumno;
            }
        } else {
            // Si no hay un alumno en sesión, redirige a la página de inicio de sesión
            return "login";
        }
    }
    @GetMapping("/irAña/{idAlumno}")
    public String irPaginaAñadirActividad(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if(alumnoSession != null){
            Long idAlumno = alumnoSession.getIdalumno();
            model.addAttribute("idAlumno", idAlumno);
            return "Pagina_AnadirActividad";
        } else {
            return "login"; // Agrega el objeto alumno al modelo para renderizar la vista de login
        }
    }

    //GET PARA AÑADIR ACTIVIDAD
    @GetMapping("/nuevaact")
    public String nuevaActividadGet( Model model, HttpServletRequest request){
        Actividad actividad = new Actividad();

        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");
        if(alumnoSession!=null){
            actividad.setAlumno(alumnoSession);
            model.addAttribute("actividad", actividad);
            model.addAttribute("idAlumno", alumnoSession.getIdalumno());
            return "Pagina_AnadirActividad"; //agregar aqui el editaractividad
        }else{
            model.addAttribute("alumno", new Alumno());
            return "login";
        }
    }



    //TODO - PUT PARA EDITAR ACTIVIDAD

    //TODO - DELETE PARA EDITAR ACTIVIDAD


    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("alumno", new Alumno());
        return "login";

    }
}
