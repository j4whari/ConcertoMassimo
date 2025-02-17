package com.example.concertomassimo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Mostra la pagina register
    @GetMapping("/showRegister")
    public String showRegisterPage() {
        return "register";  // Cerca il file in src/main/resources/templates/
    }
}
