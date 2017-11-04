package fr.polytech.rfid.serializers;

public interface Serializer<O> {

    public <I> O to(I in);
}