package mx.edu.utez.mexprotec.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import mx.edu.utez.mexprotec.models.rol.Rol;
import mx.edu.utez.mexprotec.models.users.Users;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private Integer id;
    private String nameUser;
    private String name;
    private String lastname;
    private String lastnameMatern;
    @NotNull
    private String email;
    private String phoneNumber;
    private String localitation;
    @NotNull
    @Size(min = 10, message = "La contraseña debe tener al menos 10 caracteres")
    private String password;
    private Rol rol;
    @JsonIgnore
    private Boolean status;

    public Users getUsers(){
        return new Users(
                getId(),
                getNameUser(),
                getName(),
                getLastname(),
                getLastnameMatern(),
                getEmail(),
                getPhoneNumber(),
                getLocalitation(),
                getPassword(),
                getRol(),
                getStatus()
        );
    }
}