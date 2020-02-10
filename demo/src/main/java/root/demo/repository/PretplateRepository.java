package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.demo.model.Pretplate;

public interface PretplateRepository extends JpaRepository<Pretplate, Long> {
    public Pretplate getByCasopisIssnAndAndUserName(Long casopisIssn, String userName);
}
