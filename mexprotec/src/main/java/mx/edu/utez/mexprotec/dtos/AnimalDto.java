package mx.edu.utez.mexprotec.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.animals.personality.Personality;
import mx.edu.utez.mexprotec.models.animals.race.Race;
import mx.edu.utez.mexprotec.models.animals.typePet.TypePet;
import mx.edu.utez.mexprotec.models.image.animal.AnimalImage;
import mx.edu.utez.mexprotec.models.users.Users;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnimalDto {

    private Long id;
    private String namePet;
    private TypePet typePet;
    private String location;
    private Race race;
    private String description;
    private Personality personality;
    private String sex;
    private String size;
    private Double weight;
    private Integer age;
    private String color;
    private Boolean sterilized;
    private Boolean status;
    private Users register;
    private List<String> imageUrl;

    public Animals toAnimals() {
        Animals animal = new Animals();
        animal.setId(getId());
        animal.setNamePet(getNamePet());
        animal.setTypePet(getTypePet());
        animal.setLocation(getLocation());
        animal.setRace(getRace());
        animal.setDescription(getDescription());
        animal.setPersonality(getPersonality());
        animal.setSex(getSex());
        animal.setSize(getSize());
        animal.setWeight(getWeight());
        animal.setAge(getAge());
        animal.setColor(getColor());
        animal.setSterilized(getSterilized());
        animal.setStatus(getStatus());
        animal.setRegister(getRegister());

        List<AnimalImage> animalImages = new ArrayList<>();
        if (getImageUrl() != null) {
            for (String imageUrl : getImageUrl()) {
                AnimalImage animalImage = new AnimalImage();
                animalImage.setAnimal(animal);
                animalImage.setImageUrl(imageUrl);
                animalImages.add(animalImage);
            }
        }
        animal.setImages(animalImages);
        return animal;
    }
}
