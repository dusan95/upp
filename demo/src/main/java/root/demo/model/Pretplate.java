package root.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "pretplate")
public class Pretplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "casopisName")
    String casopisName;

    @Column(name = "casopisIssn")
    Long casopisIssn;

    @Column(name = "userName")
    String userName;

    public Pretplate(){}
    public Pretplate(String casopisName, Long casopisIssn, String userName){
        this.casopisName = casopisName;
        this.casopisIssn = casopisIssn;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCasopisName() {
        return casopisName;
    }

    public void setCasopisName(String casopisName) {
        this.casopisName = casopisName;
    }

    public Long getCasopisIssn() {
        return casopisIssn;
    }

    public void setCasopisIssn(Long casopisIssn) {
        this.casopisIssn = casopisIssn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
