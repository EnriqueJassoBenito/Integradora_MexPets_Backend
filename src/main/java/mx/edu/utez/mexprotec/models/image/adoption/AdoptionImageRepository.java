package mx.edu.utez.mexprotec.models.image.adoption;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdoptionImageRepository extends JpaRepository<AdoptionImage, UUID> {
}
