package fr.polytech.rfid.controllers;

import fr.polytech.rfid.entities.Door;
import fr.polytech.rfid.entities.Log;
import fr.polytech.rfid.entities.User;
import fr.polytech.rfid.responses.ErrorResponse;
import fr.polytech.rfid.responses.SuccessResponse;
import fr.polytech.rfid.services.DoorDaoServices;
import fr.polytech.rfid.services.LogDaoServices;
import fr.polytech.rfid.services.UserDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController extends AbstractController {

    @Autowired
    private UserDaoServices userDaoServices;

    @Autowired
    private DoorDaoServices doorDaoServices;

    @Autowired
    private LogDaoServices logDaoServices;

    @RequestMapping(value = "/isGranted/{doorId}/{rfidTag}", method = RequestMethod.GET)
    public String isGranted(@PathVariable int doorId, @PathVariable String rfidTag) {
        // Create parameters in order to filter users
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("rfidTag", rfidTag);

        // Check filtering result
        final List<User> users = this.userDaoServices.filter(parameters);
        if (users.isEmpty()) {
            return SERIALIZER.to(new ErrorResponse("The provided RFID tag is not assigned!"));
        } else if (users.size() > 1) {
            return SERIALIZER.to(new ErrorResponse("The provided RFID tag is assigned to multiple users and mustn't be!"));
        }

        // Check the corresponding user is granted
        final User user = users.get(0);
        if (!user.getIsValid()) {
            return SERIALIZER.to(new ErrorResponse("The user is not granted to use its RFID tag!"));
        }

        // Check the door
        final Door door = this.doorDaoServices.get(doorId);
        if (door == null) {
            return SERIALIZER.to(new ErrorResponse("The provided door doesn't exist!"));
        }

        // Create a log
        final Log log = new Log();
        log.setUser(user);
        log.setDoor(door);
        log.setDateTime(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        this.logDaoServices.insert(log);

        // Return an agreement
        return SERIALIZER.to(new SuccessResponse("The user is granted to use its RFID tag!"));
    }
}