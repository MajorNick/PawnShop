package majornick.pawnshop.repository;

import majornick.pawnshop.domain.Branch;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepo extends JpaRepository<Branch, Long> {

    @Override
    @Cacheable(value = "branches", key = "#aLong")
    Optional<Branch> findById(Long aLong);
}
