package mx.edu.utez.mexprotec.models.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findAllByStatus(Boolean status);
    Users getById(Integer id);

    @Query(value = "SELECT * FROM users WHERE status=true AND id = :id", nativeQuery = true)
    Users findByIdAndActivo(@Param("id") Integer id);

    @Query(value = "SELECT * FROM users WHERE status=true AND email = :email", nativeQuery = true)
    Users findByEmailAndActivo(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE status=true AND id = :id AND id_rol = :id_rol", nativeQuery = true)
    Users findByRolAndUserAndActivo(@Param("id") Integer id, @Param("id_rol") Integer id_rol);


}
