package mx.edu.utez.mexprotec.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.category.Category;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

    private Long id;
    private String personality;
    private String size;
    private String race;
    //private String weight;
    private String color;
    private String specie;
    //private int age;
    //private String sex;
    //private String sociability;
    private Boolean status;

    public Category getCategory(){
        return new Category(
                getId(),
                getPersonality(),
                getSize(),
                getRace(),
                //getWeight(),
                getColor(),
                getSpecie(),
                //getAge(),
                //getSex(),
                //getSociability(),
                getStatus()
        );
    }

}
