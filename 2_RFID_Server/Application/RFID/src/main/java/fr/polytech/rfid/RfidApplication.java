package fr.polytech.rfid;

import fr.polytech.rfid.services.DoorDaoServices;
import fr.polytech.rfid.services.LogDaoServices;
import fr.polytech.rfid.services.UserDaoServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RfidApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfidApplication.class, args);
    }

    @Bean
    public DoorDaoServices doorDaoServices() {
        return new DoorDaoServices();
    }

    @Bean
    public UserDaoServices userDaoServices() {
        return new UserDaoServices();
    }

    @Bean
    public LogDaoServices logDaoServices() {
        return new LogDaoServices();
    }
}