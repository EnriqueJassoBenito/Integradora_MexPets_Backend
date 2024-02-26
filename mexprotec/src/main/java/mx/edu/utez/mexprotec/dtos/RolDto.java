package mx.edu.utez.mexprotec.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.rol.Rol;
import mx.edu.utez.mexprotec.models.users.Users;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RolDto {

    private Integer idRol;
    private String rol;
    private Boolean status;
    private Set<Users> usuario;

    public Rol getRolEntity(){
        return new Rol(
                getIdRol(),
                getRol(),
                getStatus(),
                getUsuario()
        );
    }
}
