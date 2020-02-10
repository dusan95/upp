package root.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "journal_plan")
public class JournalPlan {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String journal;

    @Column
    private String planId;

    public JournalPlan() {
    }
    public JournalPlan(String journal, String planId) {
        this.journal = journal;
        this.planId = planId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
