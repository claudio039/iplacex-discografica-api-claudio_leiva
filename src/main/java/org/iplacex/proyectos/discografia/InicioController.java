package org.iplacex.proyectos.discografia;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "Bienvenido a la discografia Iplacex";
    }
}