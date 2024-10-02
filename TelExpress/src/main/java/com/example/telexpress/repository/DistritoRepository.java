package com.example.telexpress.repository;
import com.example.telexpress.entity.Zona;
import com.example.telexpress.entity.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DistritoRepository extends JpaRepository<Distrito, Integer > {
    List<Distrito> findByZona(Zona zona);  // Método para obtener distritos por id de zona


}
