package fr.polytech.rfid.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController extends AbstractController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView root(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
		final Writer writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));

		request.setAttribute("message", writer.toString());
		return new ModelAndView("error");
	}
}