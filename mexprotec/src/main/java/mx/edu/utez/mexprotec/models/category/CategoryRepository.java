package mx.edu.utez.mexprotec.models.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query(
            value = "UPDATE category SET status = :status WHERE id_category = :id_category",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id_category") Long id
    );

    Optional<Category> findByIdCategory(Long aLong);
    List<Category> findAllByStatus(Boolean status);
    Category getByIdCategory(Long id);

}
