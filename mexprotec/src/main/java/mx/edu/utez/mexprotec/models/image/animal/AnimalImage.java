package mx.edu.utez.mexprotec.models.image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;

@Entity
@Table(name = "animal_images")
@Getter
@Setter
public class AnimalImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animals_id")
    private Animals animal;

    @Column(name = "image_url")
    private String imageUrl;
}
