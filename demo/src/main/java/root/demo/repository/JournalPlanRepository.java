package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.demo.model.JournalPlan;

@Repository
public interface JournalPlanRepository extends JpaRepository<JournalPlan, Long> {
    JournalPlan findJournalPlanByJournal(String name);
    JournalPlan findJournalPlanById(Long id);
}
