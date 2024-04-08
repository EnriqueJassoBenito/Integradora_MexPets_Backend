package mx.edu.utez.mexprotec.models.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionsRepository extends JpaRepository<Actions, Long> {

    Optional<Actions> findById(Long aLong);
    Actions getById(Long id);
}
