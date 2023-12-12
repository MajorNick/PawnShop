package majornick.pawnshop.repository;

import majornick.pawnshop.domain.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepo extends JpaRepository<PaymentHistory, Long> {
}
