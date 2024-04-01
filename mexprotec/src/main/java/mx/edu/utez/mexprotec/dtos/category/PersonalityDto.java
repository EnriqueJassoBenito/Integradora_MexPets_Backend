package mx.edu.utez.mexprotec.dtos.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.animals.personality.Personality;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonalityDto {

    private Long id;
    private String personalityPet;
    @JsonIgnore
    private List<Animals> animals;

    public Personality getPersonality(){
        return new Personality(
                getId(),
                getPersonalityPet(),
                getAnimals()
        );
    }
}
