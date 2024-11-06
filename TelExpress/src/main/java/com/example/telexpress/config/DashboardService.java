package com.example.telexpress.config;

import com.example.telexpress.repository.OrdenesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DashboardService {
    @Autowired
    private OrdenesRepository ordenesRepository;
    public Map<String, Integer> obtenerCantidadOrdenesPorMes(){
        List<Object[]> resultado = ordenesRepository.obtenerCantidadOrdenesPorMes();
        Map<String, Integer> cantidadOrdenesPorMes = new HashMap<>();
        for (Object[] row : resultado) {
            String mes = convertirNumeroMes((Integer) row[0]);
            Integer cantidad = ((Long) row[1]).intValue();
            cantidadOrdenesPorMes.put(mes, cantidad);
        }
        return cantidadOrdenesPorMes;
    }

    private String convertirNumeroMes(Integer numeroMes) {
        String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Setiembre","Octubre","Noviembre","Diciembre"};
        return meses[numeroMes-1];
    }
}
