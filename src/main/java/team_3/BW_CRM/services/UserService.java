package team_3.BW_CRM.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team_3.BW_CRM.entities.Ruolo;
import team_3.BW_CRM.entities.Utente;
import team_3.BW_CRM.exceptions.BadRequestException;
import team_3.BW_CRM.exceptions.NotFoundException;
import team_3.BW_CRM.payloads.UtenteDTO;
import team_3.BW_CRM.repositories.RuoloRepository;
import team_3.BW_CRM.repositories.UserRepository;
import team_3.BW_CRM.tools.MailgunSender;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private MailgunSender mailgunSender;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Utente findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun utente trovato con ID: " + id));
    }
    public Utente findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Nessun utente registrato con questa email"));
    }

    public List<Utente> findAllUsers() {
        return this.userRepository.findAll();
    }


    public Utente save(UtenteDTO body) {
        this.userRepository.findByEmail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );

        Utente newUser = new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(), body.cognome());

        Ruolo ruoloUser = ruoloRepository.findByTipo("USER")
                .orElseThrow(() -> new NotFoundException("Ruolo USER non trovato"));
        newUser.getRuoli().add(ruoloUser);

        Utente savedUser = this.userRepository.save(newUser);

        mailgunSender.sendRegistrationEmail(savedUser);

        return savedUser;
    }

    public String uploadAvatar(MultipartFile file, Long idUtente) {
        String url = null;
        try {
            url = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        Utente found = this.findById(idUtente);
        found.setAvatar(url);
        userRepository.save(found);
        return url;
    }

    public void assignRoleToUser(Long userId, String tipoRuolo) {
        Utente utente = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Nessun utente trovato con ID: " + userId));

        Ruolo ruolo = ruoloRepository.findByTipo(tipoRuolo)
                .orElseThrow(() -> new NotFoundException("Nessun ruolo trovato con tipo: " + tipoRuolo));


        if (utente.getRuoli().contains(ruolo)) {
            throw new BadRequestException("L'utente ha già il ruolo specificato: " + tipoRuolo);
        }

        utente.getRuoli().add(ruolo);
        userRepository.save(utente);
    }
    public void removeRoleFromUser(Long userId, String tipoRuolo) {
        Utente utente = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Nessun utente trovato con ID: " + userId));

        Ruolo ruolo = ruoloRepository.findByTipo(tipoRuolo)
                .orElseThrow(() -> new NotFoundException("Nessun ruolo trovato con tipo: " + tipoRuolo));

        if (!utente.getRuoli().contains(ruolo)) {
            throw new BadRequestException("L'utente non ha il ruolo specificato: " + tipoRuolo);
        }

        utente.getRuoli().remove(ruolo);
        userRepository.save(utente);
    }



    public Utente updateUser(Long id, UtenteDTO userDTO) {
        Utente utente = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con ID: " + id));


        utente.setUsername(userDTO.username());
        utente.setEmail(userDTO.email());
        utente.setNome(userDTO.nome());
        utente.setCognome(userDTO.cognome());


        if (userDTO.password() != null && !userDTO.password().isEmpty()) {
            utente.setPassword(bcrypt.encode(userDTO.password()));
        }

        return userRepository.save(utente);
    }
    public List<Utente> findUtenteByRuolo(String ruoloTipo) {
        Ruolo ruolo = ruoloRepository.findByTipo(ruoloTipo)
                .orElseThrow(() -> new NotFoundException("Ruolo non trovato con tipo: " + ruoloTipo));

        return userRepository.findByRuoli(ruolo);
    }


    public Utente findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Nessun utente trovato con nome: " + username));
    }


    public void deleteUser(Long id) {
        Utente utente = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con ID: " + id));

        userRepository.delete(utente);
    }
}
