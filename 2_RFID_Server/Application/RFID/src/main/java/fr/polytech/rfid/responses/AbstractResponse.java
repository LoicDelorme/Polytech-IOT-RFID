package fr.polytech.rfid.responses;

public abstract class AbstractResponse {

    private final boolean success;

    private final Object data;

    public AbstractResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Object getData() {
        return this.data;
    }
}