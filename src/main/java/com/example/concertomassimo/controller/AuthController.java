package com.example.concertomassimo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/homepage")
    public String homepage() {
        return "homepage"; // Questo cercherà form.html in src/main/resources/templates
    }
    @GetMapping("/index")
    public String home() {
        return "index"; // Questo cercherà index.html in src/main/resources/templates
    }
    // Mostra la pagina login/register
    @GetMapping("/auth")
    public String showLoginPage() {
        return "auth";  // Cerca il file in src/main/resources/templates/
    }
    @GetMapping("/form")
    public String form() {
        return "form"; // Questo cercherà form.html in src/main/resources/templates
    }

}
