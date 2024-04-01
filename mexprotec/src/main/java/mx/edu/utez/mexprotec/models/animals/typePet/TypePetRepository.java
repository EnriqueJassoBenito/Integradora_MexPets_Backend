package mx.edu.utez.mexprotec.models.animals.typePet;

import mx.edu.utez.mexprotec.models.animals.race.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypePetRepository extends JpaRepository<TypePet, Long> {

    Optional<TypePet> findById(Long aLong);
    TypePet getById(Long id);
    Optional<TypePet> findByType(String type); // Corregido a findByType

}

