package fr.polytech.rfid.services;

import fr.polytech.rfid.entities.User;

public class UserDaoServices extends AbstractDaoServices<User> {

    public UserDaoServices() {
        super(User.class);
    }
}