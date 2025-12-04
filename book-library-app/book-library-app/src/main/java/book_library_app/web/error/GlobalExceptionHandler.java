package book_library_app.web.error;


//import book_library_app.exception.DomainException;
import book_library_app.exeption.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DomainException.class)
    public String handleDomainException(DomainException ex, Model model) {
        log.error("DomainException caught: {}", ex.getMessage());

        model.addAttribute("errorTitle", "Application error");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error";
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException ex, Model model) {
        log.error("Validation error: {}", ex.getMessage());

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        model.addAttribute("errorTitle", "Invalid input");
        model.addAttribute("errorMessage", "Моля, проверете въведените данни.");
        model.addAttribute("validationErrors", allErrors);

        return "error";
    }


    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        log.error("Unexpected error: ", ex);

        model.addAttribute("errorTitle", "Unexpected error");
        model.addAttribute("errorMessage",
                "Възникна неочаквана грешка. Моля, опитайте отново по-късно.");

        return "error";
    }
}
