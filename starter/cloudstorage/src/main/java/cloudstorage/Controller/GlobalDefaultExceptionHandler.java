package cloudstorage.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;


@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler  {

    //public static final String DEFAULT_ERROR_VIEW = "error";



    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleConflict(HttpServletRequest req,Exception  ex,RuntimeException e) {
            // Nothing to do
        log.error("=> We are in the handleConflict : HttpStatus.CONFLICT");

        log.error("Request: " + req.getRequestURL() + " raised " + ex  + "runtimeException :"+e );
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException e) {
        // Implementation details...

        log.error("=> We are in the handleRuntimeException :  HttpStatus.INTERNAL_SERVER_ERROR !");
    }




    // Specify name of a specific view that will be used to display the error:
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError() {
        // Nothing to do.  Returns the logical view name of an error page, passed
        // to the view-resolver(s) in usual way.
        // Note that the exception is NOT available to this view (it is not added
        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
        // below.

        log.error("=> We are in the databaseError !");

        return "home";
    }




    @ExceptionHandler(MissingServletRequestPartException.class)
    public ModelAndView handleMyException(HttpServletRequest req,Exception  ex) {


        log.error("it appears you have an Incorrect File Upload !");

        log.error("Request: " + req.getRequestURL() + " raised " + ex );



        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("message", "Fatal error - File Upload missing");
        mav.setViewName("home");

        return mav;
    }




    @ExceptionHandler(TemplateInputException.class)
    public ModelAndView handleTemplateException(HttpServletRequest req,Exception  ex) {


        log.error("it appears you have an Incorrect Template code - Internal error !");

        log.error("Request: " + req.getRequestURL() + " raised " + ex );



        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.setViewName("home");

        return mav;
    }





    // Total control - setup a model and return the view name yourself. Or
    // consider subclassing ExceptionHandlerExceptionResolver (see below).

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) throws Exception {

        log.error("=> We are in the handleError !");

        // If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            log.error("=> We have a @ResponseStatus !");
            throw ex;
        }



        log.error("Request: " + req.getRequestURL() + " raised " + ex );


        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("home");
        return mav;
    }






}
