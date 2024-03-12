package mx.edu.utez.mexprotec.models.animals.race;

import mx.edu.utez.mexprotec.models.animals.personality.Personality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    Optional<Race> findById(Long aLong);
    Race getById(Long id);

}
