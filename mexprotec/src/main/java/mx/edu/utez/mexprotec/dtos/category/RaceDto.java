package mx.edu.utez.mexprotec.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.animals.race.Race;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RaceDto {

    private Long id;
    private String racePet;
    private List<Animals> animals;

    public Race getRace(){
        return new Race(
                getId(),
                getRacePet(),
                getAnimals()
        );
    }
}
