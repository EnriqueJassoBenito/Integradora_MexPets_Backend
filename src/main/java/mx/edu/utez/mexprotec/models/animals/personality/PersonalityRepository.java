package mx.edu.utez.mexprotec.models.animals.personality;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalityRepository extends JpaRepository<Personality, Long> {

    Optional<Personality> findById(Long aLong);
    Personality getById(Long id);
    Optional<Personality> findByPersonalityPet(String personalityPet);
}
