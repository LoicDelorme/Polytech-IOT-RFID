package fr.polytech.rfid.entities;

import java.util.Date;

public class Log {

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