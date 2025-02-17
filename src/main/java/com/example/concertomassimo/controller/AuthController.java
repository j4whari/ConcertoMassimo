package com.example.concertomassimo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Mostra la pagina register
    @GetMapping("/auth")
    public String showLoginPage() {
        return "auth";  // Cerca il file in src/main/resources/templates/
    }
    @GetMapping("/index")
    public String home() {
        return "index"; // Questo cercherà index.html in src/main/resources/templates
    }
}
