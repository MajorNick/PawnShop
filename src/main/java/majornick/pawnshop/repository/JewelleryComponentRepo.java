package majornick.pawnshop.repository;

import majornick.pawnshop.domain.JewelleryComponent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JewelleryComponentRepo extends JpaRepository<JewelleryComponent, Long> {

    @Override
    @Cacheable(value = "jew_components", key = "#aLong")
    Optional<JewelleryComponent> findById(Long aLong);
}
