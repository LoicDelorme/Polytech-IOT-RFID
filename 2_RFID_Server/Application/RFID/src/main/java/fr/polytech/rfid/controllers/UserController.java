package fr.polytech.rfid.controllers;

import fr.polytech.rfid.entities.User;
import fr.polytech.rfid.forms.UserForm;
import fr.polytech.rfid.responses.SuccessResponse;
import fr.polytech.rfid.services.UserDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/user")
public class UserController extends AbstractController {

    @Autowired
    private UserDaoServices userDaoServices;

    @RequestMapping(value = "/overview/{id}", method = RequestMethod.GET)
    public String overview(@PathVariable int id) {
        final User user = this.userDaoServices.get(id);
        return SERIALIZER.to(new SuccessResponse(user));
    }

    @RequestMapping(value = "overview/{id}/logs", method = RequestMethod.GET)
    public String logsOverview(@PathVariable int id) {
        final User user = this.userDaoServices.get(id);
        return SERIALIZER.to(new SuccessResponse(user.getLogs()));
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestBody String data) {
        final Map<String, String> parameters = DESERIALIZER.from(data, HashMap.class);

        final List<User> users = this.userDaoServices.filter(parameters);
        return SERIALIZER.to(new SuccessResponse(users));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        final List<User> users = this.userDaoServices.getAll();
        return SERIALIZER.to(new SuccessResponse(users));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String count() {
        return SERIALIZER.to(new SuccessResponse(this.userDaoServices.count()));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestBody String data) {
        final UserForm userForm = DESERIALIZER.from(data, UserForm.class);

        final User user = new User();
        user.setLastname(userForm.getLastname());
        user.setFirstname(userForm.getFirstname());
        user.setRfidTag(userForm.getRfidTag());
        user.setIsValid(userForm.isValid());

        this.userDaoServices.insert(user);
        return SERIALIZER.to(new SuccessResponse(user));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(@RequestBody String data) {
        final UserForm userForm = DESERIALIZER.from(data, UserForm.class);

        final User user = this.userDaoServices.get(userForm.getId());
        user.setLastname(userForm.getLastname());
        user.setFirstname(userForm.getFirstname());
        user.setRfidTag(userForm.getRfidTag());
        user.setIsValid(userForm.isValid());

        this.userDaoServices.update(user);
        return SERIALIZER.to(new SuccessResponse(user));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        final User user = this.userDaoServices.get(id);
        this.userDaoServices.delete(user);

        return SERIALIZER.to(new SuccessResponse());
    }
}