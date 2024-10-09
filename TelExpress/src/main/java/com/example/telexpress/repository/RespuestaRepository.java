package com.example.telexpress.repository;
import com.example.telexpress.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta,RespuestaId>{
}
