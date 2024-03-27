package mx.edu.utez.mexprotec.models.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>{

    @Modifying
    @Query(
            value = "UPDATE historial SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") Long id
    );

    Optional<History> findById(Long aLong);
    List<History> findAllByStatus(Boolean status);
    History getById(Long id);
}
