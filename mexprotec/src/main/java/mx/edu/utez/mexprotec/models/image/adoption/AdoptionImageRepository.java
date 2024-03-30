package mx.edu.utez.mexprotec.models.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionImageRepository extends JpaRepository<AdoptionImage, Long> {
    // Puedes agregar métodos adicionales de consulta si es necesario
}
