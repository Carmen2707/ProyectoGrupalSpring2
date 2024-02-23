package com.example.proyectogrupalspring2;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public Boolean validateToken(String token) {
        /* .... */

        return (token.equals("t0k3n"));
    }
}
