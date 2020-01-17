package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.demo.model.HashCodeConfirm;

public interface HashCodeRepository extends JpaRepository<HashCodeConfirm, Long> {
}