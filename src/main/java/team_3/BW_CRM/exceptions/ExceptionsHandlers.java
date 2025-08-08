package team_3.BW_CRM.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team_3.BW_CRM.payloads.ErrorsRespDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandlers {

    @ExceptionHandler(InvalidLineException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsRespDTO handleBadRequest(InvalidLineException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsRespDTO handleBadRequest(BadRequestException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsRespDTO handleAuthorizationDenied(AuthorizationDeniedException ex){
        return new ErrorsRespDTO(ex.getMessage(),LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsRespDTO handleNotFound(NotFoundException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsRespDTO handleUnathorized(UnauthorizedException ex) {
        return new ErrorsRespDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsRespDTO handleGeneric(Exception ex) {
        ex.printStackTrace();
        return new ErrorsRespDTO("Problema lato server", LocalDateTime.now());
    }
}