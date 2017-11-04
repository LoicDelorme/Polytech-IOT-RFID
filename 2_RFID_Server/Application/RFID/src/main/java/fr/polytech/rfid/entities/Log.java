package fr.polytech.rfid.entities;

import fr.polytech.rfid.entities.pks.LogPK;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "logs")
@IdClass(LogPK.class)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "door_id")
    private Door door;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Column(name = "date_time")
    private Date dateTime;

    public Door getDoor() {
        return door;
    }

    public void setDoor(final Door door) {
        this.door = door;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(final Date dateTime) {
        this.dateTime = dateTime;
    }
}