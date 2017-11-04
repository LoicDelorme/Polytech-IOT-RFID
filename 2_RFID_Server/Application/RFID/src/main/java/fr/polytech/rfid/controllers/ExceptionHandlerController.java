package fr.polytech.rfid.controllers;

import fr.polytech.rfid.responses.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

@ControllerAdvice
public class ExceptionHandlerController extends AbstractController {

    public static final String DEFAULT_ERROR_MESSAGE = "Request '%s' raised an exception:\n%s";

    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request, Exception exception) {
        final Writer writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));

        final String errorMessage = SERIALIZER.to(new ErrorResponse(String.format(DEFAULT_ERROR_MESSAGE, request.getRequestURL(), writer.toString())));
        LOGGER.error(errorMessage);
        return errorMessage;
    }
}