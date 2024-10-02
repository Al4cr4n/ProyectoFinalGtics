package com.example.telexpress.repository;
import com.example.telexpress.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoordinadorRepository extends JpaRepository<Usuario,Integer>{
    @Query(value = "SELECT u.* FROM usuario u INNER JOIN zona z ON u.idzona = z.idzona WHERE u.idroles = 3 AND z.nombrezona = :nombrezona", nativeQuery = true)
    List<Usuario> buscarAgentePorRolYZona(@Param("nombrezona") String nombrezona);


}
