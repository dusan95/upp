package root.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "magazine_table")
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    String name;

    @Column(name = "issn")
    Long issn;

    @Column(name = "scientificFields")
    String scientificFields;

    @Column(name = "payment")
    String payment;

    @Column(name = "chiefEditor")
    String chiefEditor;

    @Column(name = "active")
    String active;

    @Column(name = "reviewers")
    String reviewers;

    @Column(name = "editors")
    String editors;

    public Magazine(String name, Long issn, String scientificFields, String payment) {
        this.name = name;
        this.issn = issn;
        this.scientificFields = scientificFields;
        this.payment = payment;
    }

    public Magazine() {
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIssn() {
        return issn;
    }

    public void setIssn(Long issn) {
        this.issn = issn;
    }

    public String getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(String scientificFields) {
        this.scientificFields = scientificFields;
    }

    public String getChiefEditor() {
        return chiefEditor;
    }

    public void setChiefEditor(String chiefEditor) {
        this.chiefEditor = chiefEditor;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getReviewers() {
        return reviewers;
    }

    public void setReviewers(String reviewers) {
        this.reviewers = reviewers;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }
}
