package fr.polytech.rfid.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.polytech.rfid.entities.User;
import fr.polytech.rfid.forms.UserForm;
import fr.polytech.rfid.responses.LogsResponse;
import fr.polytech.rfid.responses.UserResponse;
import fr.polytech.rfid.responses.UsersResponse;

@Controller
@RequestMapping("/UserController")
public class UserController extends AbstractController {

	public static final String OVERVIEW_URL = "admin/user/overview/%d";

	public static final String OVERVIEW_LOGS_URL = "admin/user/overview/%d/logs";

	public static final String FILTER_URL = "admin/user/filter";

	public static final String LIST_URL = "admin/user/list";

	public static final String INSERT_URL = "admin/user/insert";

	public static final String UPDATE_URL = "admin/user/update";

	public static final String DELETE_URL = "admin/user/delete/%d";

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	public ModelAndView overview(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") int id) throws Exception {
		final UserResponse userResponse = DESERIALIZER.from(RESTFUL_REQUESTER.get(String.format(OVERVIEW_URL, id), String.class), UserResponse.class);
		if (!userResponse.isSuccess()) {
			throw new Exception("Failed to recover user...");
		}
		final User user = userResponse.getData();

		final LogsResponse logsResponse = DESERIALIZER.from(RESTFUL_REQUESTER.get(String.format(OVERVIEW_LOGS_URL, id), String.class), LogsResponse.class);
		if (!logsResponse.isSuccess()) {
			throw new Exception("Failed to recover logs of the user...");
		}
		user.setLogs(logsResponse.getData());

		request.setAttribute("user", user);
		return new ModelAndView("pages/users/overview");
	}

	@RequestMapping(value = "/filter-form", method = RequestMethod.GET)
	public ModelAndView filterForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("pages/users/filter-form");
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public ModelAndView filter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final UserForm userForm = new UserForm();
		userForm.setLastname(request.getParameter("lastname"));
		userForm.setFirstname(request.getParameter("firstname"));
		userForm.setRfidTag(request.getParameter("rfidTag"));

		final UsersResponse usersResponse = DESERIALIZER.from(RESTFUL_REQUESTER.post(FILTER_URL, userForm, String.class), UsersResponse.class);
		if (!usersResponse.isSuccess()) {
			throw new Exception("Failed to filter users...");
		}

		request.setAttribute("users", usersResponse.getData());
		return new ModelAndView("pages/users/list");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final UsersResponse usersResponse = DESERIALIZER.from(RESTFUL_REQUESTER.get(LIST_URL, String.class), UsersResponse.class);
		if (!usersResponse.isSuccess()) {
			throw new Exception("Failed to recover users...");
		}

		request.setAttribute("users", usersResponse.getData());
		return new ModelAndView("pages/users/list");
	}

	@RequestMapping(value = "/add-form", method = RequestMethod.GET)
	public ModelAndView addForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("pages/users/add-form");
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final UserForm userForm = new UserForm();
		userForm.setLastname(request.getParameter("lastname"));
		userForm.setFirstname(request.getParameter("firstname"));
		userForm.setRfidTag(request.getParameter("rfidTag"));
		userForm.setIsValid("on".equals(request.getParameter("isValid")));

		final UserResponse userResponse = DESERIALIZER.from(RESTFUL_REQUESTER.post(INSERT_URL, userForm, String.class), UserResponse.class);
		if (!userResponse.isSuccess()) {
			throw new Exception("Failed to insert user...");
		}

		request.setAttribute("message", "The user was successfully added!");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/update-form", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") int id) throws Exception {
		final UserResponse userResponse = DESERIALIZER.from(RESTFUL_REQUESTER.get(String.format(OVERVIEW_URL, id), String.class), UserResponse.class);
		if (!userResponse.isSuccess()) {
			throw new Exception("Failed to recover user...");
		}

		request.setAttribute("user", userResponse.getData());
		return new ModelAndView("pages/users/update-form");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final UserForm userForm = new UserForm();
		userForm.setId(Integer.parseInt(request.getParameter("id")));
		userForm.setLastname(request.getParameter("lastname"));
		userForm.setFirstname(request.getParameter("firstname"));
		userForm.setRfidTag(request.getParameter("rfidTag"));
		userForm.setIsValid("on".equals(request.getParameter("isValid")));

		final UserResponse userResponse = DESERIALIZER.from(RESTFUL_REQUESTER.put(UPDATE_URL, userForm, String.class), UserResponse.class);
		if (!userResponse.isSuccess()) {
			throw new Exception("Failed to update user...");
		}

		request.setAttribute("message", "The user was successfully updated!");
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") int id) throws Exception {
		final UserResponse userResponse = DESERIALIZER.from(RESTFUL_REQUESTER.delete(String.format(DELETE_URL, id), String.class), UserResponse.class);
		if (!userResponse.isSuccess()) {
			throw new Exception("Failed to delete user...");
		}

		request.setAttribute("message", "The user was successfully deleted!");
		return new ModelAndView("index");
	}
}