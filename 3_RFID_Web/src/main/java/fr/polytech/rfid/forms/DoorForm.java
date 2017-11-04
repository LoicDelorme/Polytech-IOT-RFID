package fr.polytech.rfid.forms;

public class DoorForm {

	private Integer id;

	private String label;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label == null || label.isEmpty() ? null : label;
	}
}