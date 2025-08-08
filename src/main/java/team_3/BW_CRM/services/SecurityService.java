package team_3.BW_CRM.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team_3.BW_CRM.entities.Utente;
import team_3.BW_CRM.exceptions.UnauthorizedException;
import team_3.BW_CRM.payloads.LoginDTO;
import team_3.BW_CRM.tools.JWT;

@Service
public class SecurityService {

    @Autowired
    private UserService userService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Utente found = this.userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }


}