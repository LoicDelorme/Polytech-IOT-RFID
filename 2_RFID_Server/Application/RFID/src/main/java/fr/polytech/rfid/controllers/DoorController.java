package fr.polytech.rfid.controllers;

import fr.polytech.rfid.entities.Door;
import fr.polytech.rfid.forms.DoorForm;
import fr.polytech.rfid.responses.SuccessResponse;
import fr.polytech.rfid.services.DoorDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/door")
public class DoorController extends AbstractController {

    @Autowired
    private DoorDaoServices doorDaoServices;

    @RequestMapping(value = "/overview/{id}", method = RequestMethod.GET)
    public String overview(@PathVariable int id) {
        final Door door = this.doorDaoServices.get(id);
        return SERIALIZER.to(new SuccessResponse(door));
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestBody String data) {
        final Map<String, String> parameters = DESERIALIZER.from(data, HashMap.class);

        final List<Door> doors = this.doorDaoServices.filter(parameters);
        return SERIALIZER.to(new SuccessResponse(doors));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        final List<Door> doors = this.doorDaoServices.getAll();
        return SERIALIZER.to(new SuccessResponse(doors));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String count() {
        return SERIALIZER.to(new SuccessResponse(this.doorDaoServices.count()));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestBody String data) {
        final DoorForm doorForm = DESERIALIZER.from(data, DoorForm.class);

        final Door door = new Door();
        door.setLabel(doorForm.getLabel());

        this.doorDaoServices.insert(door);
        return SERIALIZER.to(new SuccessResponse(door));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(@RequestBody String data) {
        final DoorForm doorForm = DESERIALIZER.from(data, DoorForm.class);

        final Door door = this.doorDaoServices.get(doorForm.getId());
        door.setLabel(doorForm.getLabel());

        this.doorDaoServices.update(door);
        return SERIALIZER.to(new SuccessResponse(door));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        final Door door = this.doorDaoServices.get(id);
        this.doorDaoServices.delete(door);

        return SERIALIZER.to(new SuccessResponse());
    }
}