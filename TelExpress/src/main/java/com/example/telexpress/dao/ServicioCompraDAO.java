package com.example.telexpress.dao;

import com.example.telexpress.dto.DatosCompra;
import com.example.telexpress.repository.TarjetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioCompraDAO {
    @Autowired
    private TarjetasRepository tarjetasRepository;

    public String realizarCompra(DatosCompra requestCompra) {

        return "allow";
    }
}
