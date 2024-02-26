package mx.edu.utez.mexprotec.models.adoption;

import mx.edu.utez.mexprotec.models.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    @Modifying
    @Query(
            value = "UPDATE adoption SET status = :status WHERE id_adoption = :id_adoption",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id_adoption") Long id
    );

    Optional<Adoption> findByIdAdoption(Long aLong);
    List<Adoption> findAllByStatus(Boolean status);
    Adoption getByIdAdoption(Long id);
}
