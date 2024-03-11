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
            value = "UPDATE adoption SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    Optional<Adoption> findById(Long aLong);
    List<Adoption> findAllByStatus(Boolean status);
    Adoption getById(Long id);
}
