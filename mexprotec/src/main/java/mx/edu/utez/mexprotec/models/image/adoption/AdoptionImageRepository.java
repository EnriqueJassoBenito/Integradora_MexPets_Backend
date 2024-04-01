package mx.edu.utez.mexprotec.models.image.adoption;

import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionImageRepository extends JpaRepository<AdoptionImage, Long> {
    // Puedes agregar métodos adicionales de consulta si es necesario
}
