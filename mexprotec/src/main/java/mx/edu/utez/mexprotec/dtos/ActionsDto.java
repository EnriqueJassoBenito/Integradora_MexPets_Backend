package mx.edu.utez.mexprotec.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.action.Actions;
import mx.edu.utez.mexprotec.models.users.Users;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActionsDto {

    private Long id;
    private Users user;
    private Boolean registerAdo;
    private Boolean adoption;

    public Actions getActions(){
        return new Actions(
                getId(),
                getUser(),
                getRegisterAdo(),
                getAdoption()
        );
    }
}
