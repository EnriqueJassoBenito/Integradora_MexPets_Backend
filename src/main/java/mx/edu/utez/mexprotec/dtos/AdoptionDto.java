package mx.edu.utez.mexprotec.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImage;
import mx.edu.utez.mexprotec.models.users.Users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdoptionDto {

    private Long id;
    private Animals animal;
    private Users cliente;
    private String description;
    //@JsonIgnore
    private Boolean status;
    private LocalDate creationDate; // Nuevo campo para la fecha de creación
    private List<String> imageUrls; // Lista de URLs de imágenes asociadas

    public Adoption toAdoption() {
        Adoption adoption = new Adoption();
        adoption.setId(id);
        adoption.setAnimal(animal);
        adoption.setCliente(cliente);
        adoption.setDescription(description);
        adoption.setStatus(status);
        adoption.setCreationDate(creationDate);
        List<AdoptionImage> adoptionImages = new ArrayList<>();
        if (imageUrls != null) {
            for (String url : imageUrls) {
                AdoptionImage adoptionImage = new AdoptionImage();
                adoptionImage.setAdoption(adoption);
                adoptionImage.setImageUrl(url);
                adoptionImages.add(adoptionImage);
            }
        }
        adoption.setImages(adoptionImages);
        return adoption;
    }

}
