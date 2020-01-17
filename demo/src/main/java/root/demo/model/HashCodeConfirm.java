package root.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "hash_code_table")
public class HashCodeConfirm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "username")
    private String username;

    @Column(name = "confirmed")
    private String confirmed;

    @Column(name = "hash_code")
    private String hashCode;

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }


    public HashCodeConfirm() {
    }

    public HashCodeConfirm(String username, String hashCode, String confirmed) {
        this.hashCode = hashCode;
        this.username = username;
        this.confirmed = confirmed;
    }
}
