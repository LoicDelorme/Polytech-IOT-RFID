package fr.polytech.rfid.responses;

public class SuccessResponse extends AbstractResponse {

    public SuccessResponse() {
        this(null);
    }

    public SuccessResponse(Object data) {
        super(true, data);
    }
}