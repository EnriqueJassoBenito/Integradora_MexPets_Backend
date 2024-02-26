package mx.edu.utez.mexprotec.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.users.Users;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnimalDto {

    private Long idAnimal;
    private String typePet;
    private String namePet;
    private String location;
    private String race;
    private String description;
    private String personality;
    private String sex;
    private String size;
    private Double weight;
    private Integer age;
    private String color;
    private Boolean sociable;
    private Boolean sterilized;
    private String image;
    @JsonIgnore
    private Boolean status;
    private Users registrador;

    public Animals getAdnimals(){
        return new Animals(
                getIdAnimal(),
                getTypePet(),
                getNamePet(),
                getLocation(),
                getRace(),
                getDescription(),
                getPersonality(),
                getSex(),
                getSize(),
                getWeight(),
                getAge(),
                getColor(),
                getSociable(),
                getSterilized(),
                getImage(),
                getStatus(),
                getRegistrador()
        );
    }
}
