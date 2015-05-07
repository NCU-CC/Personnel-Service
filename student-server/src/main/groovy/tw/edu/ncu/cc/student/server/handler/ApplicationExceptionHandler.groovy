package tw.edu.ncu.cc.student.server.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.HttpStatusCodeException

import javax.servlet.http.HttpServletRequest

@ControllerAdvice
public class ApplicationExceptionHandler {

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    @ExceptionHandler( AccessDeniedException.class )
    public ResponseEntity< Error > accessDenied( HttpServletRequest request ) {
        logger.warn( "ACCESS DENIED FOR {} FROM {}", request.getRequestURI(), request.getRemoteAddr() );
        return new ResponseEntity<>(
                "access is denied", HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler( [ HttpStatusCodeException.class, HttpServerErrorException.class ] )
    public ResponseEntity< Error > remoteResponseError( HttpStatusCodeException e ) {
        switch ( e.getStatusCode() ) {
            case HttpStatus.NOT_FOUND:
                return new ResponseEntity<>(
                        "required resource not exist", HttpStatus.NOT_FOUND
                );
            default:
                logger.error( "REQUEST FAILED FROM REMOTE SERVICE" );
                return new ResponseEntity<>(
                        e.getMessage() + " ->> " + e.getResponseBodyAsString(), e.getStatusCode()
                );
        }
    }

    @ExceptionHandler( MissingServletRequestParameterException )
    public ResponseEntity missingParameter( MissingServletRequestParameterException e ) {
        new ResponseEntity<>(
                "missing parameters:" + e.parameterName, HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler( TypeMismatchException )
    public ResponseEntity badArgument( TypeMismatchException e ) {
        new ResponseEntity<>(
                "bad argument:" + e.getValue() , HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity unhandledException( Exception e ) {
        logger.error( "UNEXPECTED ERROR", e )
        new ResponseEntity<>(
                e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

}
