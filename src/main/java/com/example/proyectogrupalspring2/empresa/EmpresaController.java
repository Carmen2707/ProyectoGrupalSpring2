package com.example.proyectogrupalspring.empresa;

import com.example.proyectogrupalspring.SecurityService;
import com.example.proyectogrupalspring.actividad.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    @Autowired //inicializar los componentes de sprint de forma automatica
    private EmpresaRepository empresaRepository;

    @Autowired
    private SecurityService security;
    @PostMapping("/post")
    public ResponseEntity<Empresa> registrarEmpresa(@RequestBody Empresa empresa, @RequestParam String token) {
        if (security.validateToken(token)) {
            return new ResponseEntity<>(empresaRepository.save(empresa), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
