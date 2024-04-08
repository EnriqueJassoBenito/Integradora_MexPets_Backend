package mx.edu.utez.mexprotec.models.processed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessedRepository extends JpaRepository<Processed, Long>{

    @Modifying
    @Query(
            value = "UPDATE adoption_processed SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    Optional<Processed> findById(Long aLong);
    List<Processed> findAllByStatus(Boolean status);
    Processed getById(Long id);

}
