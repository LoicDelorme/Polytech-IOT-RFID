package fr.polytech.rfid.forms;

public class UserForm {

	private Integer id;

	private String lastname;

	private String firstname;

	private String rfidTag;

	private Boolean isValid;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname == null || lastname.isEmpty() ? null : lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname == null || firstname.isEmpty() ? null : firstname;
	}

	public String getRfidTag() {
		return this.rfidTag;
	}

	public void setRfidTag(final String rfidTag) {
		this.rfidTag = rfidTag == null || rfidTag.isEmpty() ? null : rfidTag;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(final Boolean isValid) {
		this.isValid = isValid;
	}
}