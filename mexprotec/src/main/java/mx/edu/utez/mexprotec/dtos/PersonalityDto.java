package mx.edu.utez.mexprotec.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.personality.Personality;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonalityDto {

    private Long id;
    private String personalityPet;

    public Personality getPersonality(){
        return new Personality(
                getId(),
                getPersonalityPet()
        );
    }
}
