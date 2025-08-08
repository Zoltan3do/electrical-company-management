package team_3.BW_CRM.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team_3.BW_CRM.entities.Utente;
import team_3.BW_CRM.exceptions.BadRequestException;
import team_3.BW_CRM.payloads.LoginDTO;
import team_3.BW_CRM.payloads.UtenteDTO;
import team_3.BW_CRM.payloads.UtenteLoginResponseDTO;
import team_3.BW_CRM.services.SecurityService;
import team_3.BW_CRM.services.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private SecurityService ss;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UtenteLoginResponseDTO LoginResponseDTO(@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return new UtenteLoginResponseDTO(this.ss.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente save(@RequestBody @Validated UtenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.userService.save(body);
    }
}


