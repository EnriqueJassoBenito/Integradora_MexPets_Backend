package mx.edu.utez.mexprotec.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.race.Race;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RaceDto {

    private Long id;
    private String racePet;

    public Race getRace(){
        return new Race(
                getId(),
                getRacePet()
        );
    }
}
