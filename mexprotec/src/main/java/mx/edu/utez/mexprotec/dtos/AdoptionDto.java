package mx.edu.utez.mexprotec.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.users.Users;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdoptionDto {

    private Long idAdoption;
    private Animals animal;
    private Users cliente;
    private String name;
    private String email;
    private String photoUrl;
    @JsonIgnore
    private Boolean status;

    public Adoption getAdoption(){
        return new Adoption(
                getIdAdoption(),
                getAnimal(),
                getCliente(),
                getName(),
                getEmail(),
                getPhotoUrl(),
                getStatus()
        );
    }
}
