package mx.edu.utez.mexprotec.models.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol getByIdRol(Integer id);

    Rol findByRol(String rol);
}
