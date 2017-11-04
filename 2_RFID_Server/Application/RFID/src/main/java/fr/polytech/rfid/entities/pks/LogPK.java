package fr.polytech.rfid.entities.pks;

import fr.polytech.rfid.entities.Door;
import fr.polytech.rfid.entities.User;

import java.io.Serializable;
import java.util.Date;

public class LogPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Door door;

    private User user;

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