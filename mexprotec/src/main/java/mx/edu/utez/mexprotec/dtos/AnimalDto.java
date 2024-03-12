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

    private Long id;
    private String namePet;
    private String typePet;
    private String location;
    private String race;
    private String description;
    private String personality;
    private String sex;
    private String size;
    private Double weight;
    private Integer age;
    private String color;
    private Boolean sterilized;
    private String image;
    //@JsonIgnore
    private Boolean status;
    private Users register;

    public Animals getAdnimals(){
        return new Animals(
                getId(),
                getNamePet(),
                getTypePet(),
                getLocation(),
                getRace(),
                getDescription(),
                getPersonality(),
                getSex(),
                getSize(),
                getWeight(),
                getAge(),
                getColor(),
                getSterilized(),
                getImage(),
                getStatus(),
                getRegister()
        );
    }
}
