package mx.edu.utez.mexprotec.models.animals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalsRepository extends JpaRepository<Animals, Long> {

    /*@Modifying
    @Query(
            value = "UPDATE animals SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );*/

    Optional<Animals> findById(Long aLong);
    //List<Animals> findAllByStatus(Boolean status);
    Animals getById(Long id);

    List<Animals> findByApprovalStatus(ApprovalStatus approvalStatus);
}
