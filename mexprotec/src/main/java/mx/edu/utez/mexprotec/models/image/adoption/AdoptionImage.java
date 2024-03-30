package mx.edu.utez.mexprotec.models.image;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.adoption.Adoption;

@Entity
@Table(name = "adoption_images")
@Getter
@Setter
public class AdoptionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "adoption_id")
    private Adoption adoption;

    @Column(name = "image_url")
    private String imageUrl;

    // Otros campos y métodos necesarios
}
