package majornick.pawnshop.repository;

import majornick.pawnshop.domain.Item;
import majornick.pawnshop.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.status = :status AND FUNCTION('DAY', CAST(i.pawnDate AS date)) = FUNCTION('DAY', CAST(:today AS date))")
    List<Item> findAllByStatusAndDayBefore28(@Param("status") Status status, @Param("today") LocalDate today);

    @Query("SELECT i FROM Item i WHERE i.status = :status AND FUNCTION('DAY', CAST(i.pawnDate AS date)) >= 28")
    List<Item> findAllByStatusAndDayAfter28(@Param("status") Status status);
}
