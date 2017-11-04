package fr.polytech.rfid.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController extends AbstractController {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, Exception exception) {
		final Writer writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));

		request.setAttribute("message", String.format("Request '%s' raised an exception:\n%s", request.getRequestURL(), writer.toString()));
		return new ModelAndView("error");
	}
}