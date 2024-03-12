package mx.edu.utez.mexprotec.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.typePet.TypePet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypePetDto {

    private Long id;
    private String type;

    public TypePet getTypePet(){
        return new TypePet(
                getId(),
                getType()
        );
    }
}
