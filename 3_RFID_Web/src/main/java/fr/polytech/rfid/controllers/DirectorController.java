package fr.polytech.rfid.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.polytech.rfid.forms.DoorForm;
import fr.polytech.rfid.responses.DoorResponse;
import fr.polytech.rfid.responses.DoorsResponse;

@Controller
@RequestMapping("/DoorController")
public class DirectorController extends AbstractController {

	public static final String OVERVIEW_URL = "admin/door/overview/%d";

	public static final String FILTER_URL = "admin/door/filter";

	public static final String LIST_URL = "admin/door/list";

	public static final String INSERT_URL = "admin/door/insert";

	public static final String UPDATE_URL = "admin/door/update";

	public static final String DELETE_URL = "admin/door/delete/%d";

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	public ModelAndView overview(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") int id) throws Exception {
		final DoorResponse doorResponse = DESERIALIZER.from(RESTFUL_REQUESTER.get(String.format(OVERVIEW_URL, id), String.class), DoorResponse.class);
		if (!doorResponse.isSuccess()) {
			throw new Exception("Failed to recover door...");
		}

		request.setAttribute("door", doorResponse.getData());
		return new ModelAndView("pages/doors/overview");
	}

	@RequestMapping(value = "/filter-form", method = RequestMethod.GET)
	public ModelAndView filterForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("pages/doors/filter-form");
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public ModelAndView filter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final DoorForm doorForm = new DoorForm();
		doorForm.setLabel(request.getParameter("label"));

		final DoorsResponse doorsResponse = DESERIALIZER.from(RESTFUL_REQUESTER.post(FILTER_URL, doorForm, String.class), DoorsResponse.class);
		if (!doorsResponse.isSuccess()) {
			throw new Exception("Failed to filter doors...");
		}

		request.setAttribute("doors", doorsResponse.getData());
		return new ModelAndView("pages/doors/list");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final DoorsResponse doorsResponse = DESERIALIZER.from(RESTFUL_REQUESTER.get(LIST_URL, String.class), DoorsResponse.class);
		if (!doorsResponse.isSuccess()) {
			throw new Exception("Failed to recover doors...");
		}

		request.setAttribute("doors", doorsResponse.getData());
		return new ModelAndView("pages/doors/list");
	}

	@RequestMapping(value = "/add-form", method = RequestMethod.GET)
	public ModelAndView addForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("pages/doors/add-form");
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final DoorForm doorForm = new DoorForm();
		doorForm.setLabel(request.getParameter("label"));

		final DoorResponse doorResponse = DESERIALIZER.from(RESTFUL_REQUESTER.post(INSERT_URL, doorForm, String.class), DoorResponse.class);
		if (!doorResponse.isSuccess()) {
			throw new Exception("Failed to insert door...");
		}

		request.setAttribute("message", "The door was successfully added!");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/update-form", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") int id) throws Exception {
		final DoorResponse doorResponse = DESERIALIZER.from(RESTFUL_REQUESTER.get(String.format(OVERVIEW_URL, id), String.class), DoorResponse.class);
		if (!doorResponse.isSuccess()) {
			throw new Exception("Failed to recover door...");
		}

		request.setAttribute("door", doorResponse.getData());
		return new ModelAndView("pages/doors/update-form");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final DoorForm doorForm = new DoorForm();
		doorForm.setId(Integer.parseInt(request.getParameter("id")));
		doorForm.setLabel(request.getParameter("label"));

		final DoorResponse doorResponse = DESERIALIZER.from(RESTFUL_REQUESTER.put(UPDATE_URL, doorForm, String.class), DoorResponse.class);
		if (!doorResponse.isSuccess()) {
			throw new Exception("Failed to update door...");
		}

		request.setAttribute("message", "The door was successfully updated!");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") int id) throws Exception {
		final DoorResponse doorResponse = DESERIALIZER.from(RESTFUL_REQUESTER.delete(String.format(DELETE_URL, id), String.class), DoorResponse.class);
		if (!doorResponse.isSuccess()) {
			throw new Exception("Failed to delete door...");
		}

		request.setAttribute("message", "The door was successfully deleted!");
		return new ModelAndView("index");
	}
}