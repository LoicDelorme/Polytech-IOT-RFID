package fr.polytech.rfid.forms;

public class UserForm {

    private int id;

    private String lastname;

    private String firstname;

    private String rfidTag;

    private boolean isValid;

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

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(final boolean isValid) {
        this.isValid = isValid;
    }
}