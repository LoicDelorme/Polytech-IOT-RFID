package fr.polytech.rfid.entities;

import org.hibernate.validator.constraints.Length;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Length(max = 100)
    @Column(name = "lastname")
    private String lastname;

    @NotNull
    @Length(max = 100)
    @Column(name = "firstname")
    private String firstname;

    @Length(max = 100)
    @Column(name = "rfid_tag")
    private String rfidTag;

    @NotNull
    @Column(name = "is_valid")
    private boolean isValid;

    @JsonbTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Log> logs = new ArrayList<Log>();

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getRfidTag() {
        return rfidTag;
    }

    public void setRfidTag(final String rfidTag) {
        this.rfidTag = rfidTag;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(final List<Log> logs) {
        this.logs = logs;
    }

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(final boolean isValid) {
        this.isValid = isValid;
    }
}