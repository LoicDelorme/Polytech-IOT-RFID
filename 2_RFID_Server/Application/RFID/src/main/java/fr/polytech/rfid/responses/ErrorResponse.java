package fr.polytech.rfid.responses;

public class ErrorResponse extends AbstractResponse {

    public ErrorResponse() {
        this(null);
    }

    public ErrorResponse(Object data) {
        super(false, data);
    }
}