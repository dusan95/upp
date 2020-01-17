package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.demo.model.NaucneOblasti;

public interface NaucneOblastiRepo extends JpaRepository<NaucneOblasti, Long> {
}
