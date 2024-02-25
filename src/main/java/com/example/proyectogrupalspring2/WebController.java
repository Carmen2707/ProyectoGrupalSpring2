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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
/**
 * Controlador web que gestiona las solicitudes relacionadas con la aplicación de gestión de actividades para alumnos.
 * Este controlador maneja las operaciones de inicio de sesión, visualización de actividades, añadir actividad,
 * editar actividad, eliminar actividad y cerrar sesión.
 */
@Controller
@RequestMapping("/")
public class WebController {
    @Autowired //inicializar los componentes de sprint de forma automatica
    private AlumnoRepository alumnoRepository;

    @Autowired //inicializar los componentes de sprint de forma automatica
    private ActividadRepository actividadRepository;

    /**
     * @param model el modelo utilizado para pasar datos a la vista
     * @return la vista de inicio de sesión, representada por el archivo HTML "login.html"
     */
    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "login"; //Redirige al html login.html
    }

    /**
     * Método que verifica las credenciales del alumno y redirige a la página de alumno correspondiente si las credenciales son válidas.
     *
     * @param alumno  el objeto Alumno que contiene las credenciales proporcionadas por el usuario
     * @param request la solicitud HTTP
     * @return la página del alumno correspondiente si las credenciales son válidas; de lo contrario, redirige de vuelta a la página de inicio de sesión
     */
    @GetMapping("/alumCorrect")
    public String verificarAlumno(@ModelAttribute Alumno alumno, HttpServletRequest request) {
        Boolean existencia = alumnoRepository.existsAlumnoByEmail(alumno.getEmail());

        if (existencia) {
            Alumno alumnoBBDD = alumnoRepository.getAlumnoByEmail(alumno.getEmail());
            if (alumnoBBDD.getContrasenya().equals(alumno.getContrasenya())) {
                HttpSession session = request.getSession();
                session.setAttribute("alumno", alumnoBBDD);
                return "redirect:/irAlum/" + alumnoBBDD.getIdalumno();
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Método que muestra la página del alumno con sus actividades.
     *
     * @param idAlumno el ID del alumno cuya página se va a mostrar
     * @param model    el modelo utilizado para pasar datos a la vista
     * @param request  la solicitud HTTP
     * @return la vista de la página del alumno con sus actividades
     */
    @GetMapping("/irAlum/{idAlumno}")
    public String irPaginaAlumno(@PathVariable Long idAlumno, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) {
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

    /**
     * Método que muestra las actividades del alumno.
     *
     * @param model   el modelo utilizado para pasar datos a la vista
     * @param request la solicitud HTTP
     * @return la vista de las actividades del alumno
     */
    @GetMapping("/actividades")
    public String mostrarActividades(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) {
            Alumno alumno = alumnoSession;
            List<Actividad> actividades = actividadRepository.getAllByIdAlumno(alumno);

            if (!actividades.isEmpty()) {
                Long idActividad = actividades.get(0).getIdactividad();
                model.addAttribute("idActividad", idActividad);
            }

            model.addAttribute("alumno", alumno);
            model.addAttribute("actividades", actividades);
            return "Pagina_Alumno";
        } else {
            return "redirect:/login";
        }
    }


    /**
     * Método que añade una nueva actividad para un alumno.
     *
     * @param idAlumno   el ID del alumno al que se le va a añadir la actividad
     * @param fecha      la fecha de la actividad
     * @param horas      las horas dedicadas a la actividad
     * @param tipo       el tipo de actividad
     * @param actividad  la descripción de la actividad
     * @param observacion la observación de la actividad
     * @param request    la solicitud HTTP
     * @return la página del alumno después de añadir la actividad
     */
    @PostMapping("/nuevaAct/{idAlumno}")
    public String nuevaActividadPost(@PathVariable Long idAlumno, @RequestParam String fecha, @RequestParam Integer horas, @RequestParam String tipo, @RequestParam String actividad,
                                     @RequestParam String observacion, HttpServletRequest request) {

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

    /**
     * Método que muestra la página para añadir una nueva actividad.
     *
     * @param model   el modelo utilizado para pasar datos a la vista
     * @param request la solicitud HTTP
     * @return la vista para añadir una nueva actividad
     */
    @GetMapping("/irAña/{idAlumno}")
    public String irPaginaAñadirActividad(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) {
            Long idAlumno = alumnoSession.getIdalumno();
            model.addAttribute("idAlumno", idAlumno);
            return "Pagina_AnadirActividad";
        } else {
            return "login"; // Agrega el objeto alumno al modelo para renderizar la vista de login
        }
    }

    /**
     * Método que muestra la página para añadir una nueva actividad.
     *
     * @param model   el modelo utilizado para pasar datos a la vista
     * @param request la solicitud HTTP
     * @return la vista para añadir una nueva actividad
     */
    @GetMapping("/nuevaact")
    public String nuevaActividadGet(Model model, HttpServletRequest request) {
        Actividad actividad = new Actividad();

        HttpSession session = request.getSession();
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");
        if (alumnoSession != null) {
            actividad.setAlumno(alumnoSession);
            model.addAttribute("actividad", actividad);
            model.addAttribute("idAlumno", alumnoSession.getIdalumno());
            return "Pagina_AnadirActividad";
        } else {
            model.addAttribute("alumno", new Alumno());
            return "login";
        }
    }


    /**
     * Método que muestra la página para editar una actividad.
     *
     * @param idActividad el ID de la actividad que se va a editar
     * @param idAlumno    el ID del alumno al que pertenece la actividad
     * @param fecha       la fecha de la actividad
     * @param tipo        el tipo de actividad
     * @param horas       las horas dedicadas a la actividad
     * @param actividad   la descripción de la actividad
     * @param observacion la observación de la actividad
     * @param model       el modelo utilizado para pasar datos a la vista
     * @return la vista para editar la actividad
     */
    @GetMapping("/detalleActividad/{idActividad}")
    public String mostrarDetalleActividad(@PathVariable("idActividad") String idActividad,
                                          @RequestParam("idAlumno") Long idAlumno,
                                          @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
                                          @RequestParam("tipo") String tipo,
                                          @RequestParam("horas") Integer horas,
                                          @RequestParam("actividad") String actividad,
                                          @RequestParam("observacion") String observacion,
                                          Model model) {
        // Puedes pasar los datos recuperados del parámetro de consulta al modelo
        model.addAttribute("idActividad", idActividad);
        model.addAttribute("idAlumno", idAlumno);
        model.addAttribute("fecha", fecha);
        model.addAttribute("tipo", tipo);
        model.addAttribute("horas", horas);
        model.addAttribute("actividad", actividad);
        model.addAttribute("observacion", observacion);

        // Devuelve la vista para mostrar los detalles de la actividad
        return "Pagina_EditarActividad";
    }

    /**
     * Método que edita una actividad existente.
     *
     * @param idActividad el ID de la actividad que se va a editar
     * @param fecha       la fecha de la actividad
     * @param horas       las horas dedicadas a la actividad
     * @param tipo        el tipo de actividad
     * @param actividad   la descripción de la actividad
     * @param observacion la observación de la actividad
     * @param request     la solicitud HTTP
     * @return la página del alumno después de editar la actividad
     */
    @PostMapping("/editar/{idActividad}")
    public String editActivityPost(@PathVariable Long idActividad, @RequestParam String fecha, @RequestParam Integer horas,
                                   @RequestParam String tipo, @RequestParam String actividad, @RequestParam String observacion,
                                   HttpServletRequest request) {
        // Obtiene la sesión actual del usuario
        HttpSession session = request.getSession();
        // Verifica si hay un alumno en sesión
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) { // Si hay un alumno en sesión
            // Busca la actividad en la base de datos por su ID
            Actividad actividadExistente = actividadRepository.findById(idActividad).orElse(null);

            // Verifica si la actividad existe
            if (actividadExistente != null) {
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
                        return "redirect:/detalleActividad/" + idActividad;
                    }

                    // Establece los nuevos valores para la actividad existente
                    actividadExistente.setFecha(fechaDate);
                    actividadExistente.setHoras(horas);
                    actividadExistente.setTipo(tipo);
                    actividadExistente.setActividad(actividad);
                    actividadExistente.setObservacion(observacion);

                    // Guarda la actividad actualizada en la base de datos
                    actividadRepository.save(actividadExistente);

                    // Redirige a la página del alumno después de guardar la actividad
                    return "redirect:/irAlum/" + alumnoSession.getIdalumno();
                } else {
                    // Redirige de vuelta a la página de detalle de actividad si faltan datos
                    return "redirect:/detalleActividad/" + idActividad;
                }
            } else {
                // Si no se encuentra la actividad, redirige a la página de detalle de actividad
                return "redirect:/detalleActividad/" + idActividad;
            }
        } else {
        // Si no hay un alumno en sesión, redirige a la página de inicio de sesión
        return "login";
        }
    }

    /**
     * Método que elimina una actividad existente.
     *
     * @param idActividad el ID de la actividad que se va a eliminar
     * @param request     la solicitud HTTP
     * @return la página del alumno después de eliminar la actividad
     */
    @PostMapping("/eliminar/{idActividad}")
    public String eliminaActividad(@PathVariable Long idActividad, HttpServletRequest request) {
        // Obtenemos la sesion del usuario
        HttpSession session = request.getSession();
        // Verifica si hay un alumno en sesión
        Alumno alumnoSession = (Alumno) session.getAttribute("alumno");

        if (alumnoSession != null) { // Si hay un alumno en sesión
            // Busca la actividad en la base de datos por su ID
            Optional<Actividad> actividadOptional = actividadRepository.findById(idActividad);
            // Verifica si la actividad existe
            if (actividadOptional.isPresent()) {
                actividadRepository.delete(actividadOptional.get());
            }
            // Redirige a la página del alumno después de eliminar la actividad
            return "redirect:/irAlum/" + alumnoSession.getIdalumno();
        } else {
            // Si no hay un alumno en sesión, redirige a la página de login
            return "login";
        }
    }

    /**
     * Método que maneja las solicitudes GET para cerrar sesión.
     * @param model el modelo utilizado para pasar datos a la vista
     * @param request la solicitud HTTP
     * @return la vista de inicio de sesión, representada por el archivo HTML "login.html"
     */
    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("alumno", new Alumno());
        return "login";

    }
}
