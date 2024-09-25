package com.example.telexpress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("")
    public String index(){

        return "SuperAdmin/inicio_superadmin";
    }

    @GetMapping("/dashboard_superadmin")
    public String dashboardSuperadmin() {


        return "SuperAdmin/dashboard_superadmin";
    }
}
