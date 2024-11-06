package com.example.telexpress.controller;

import com.example.telexpress.dao.ApisDni;
import com.example.telexpress.dto.PersonaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConsultandoDniController {
    @Autowired
    private ApisDni apisNetDao;

    @GetMapping("/usuario/dni/{dni}")
    public ResponseEntity<PersonaDTO> obtenerdatosUsuario(@PathVariable String dni){
        PersonaDTO persona = apisNetDao.obtenerdatospordni(dni);
        if(persona != null){
            return ResponseEntity.ok(persona);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
