/*package com.example.telexpress.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            // Diferentes códigos de error
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorMessage", "Página no encontrada");
                return "Sistema/error";  // Página para 404
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorMessage", "Error interno del servidor");
                return "Sistema/error";  // Página para 500
            }
        }

        // Página para otros errores
        model.addAttribute("errorMessage", "Ha ocurrido un error");
        return "Sistema/error";
    }

}
*/