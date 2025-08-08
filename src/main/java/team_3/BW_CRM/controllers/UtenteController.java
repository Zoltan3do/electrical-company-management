package team_3.BW_CRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team_3.BW_CRM.entities.Utente;
import team_3.BW_CRM.payloads.UtenteDTO;
import team_3.BW_CRM.services.ClienteService;
import team_3.BW_CRM.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentUtente) {
        return currentUtente;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentUtente, @RequestBody @Validated UtenteDTO body) {
        return userService.updateUser(currentUtente.getId(), body);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUserById(@PathVariable Long id) {
        Utente user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Utente> getUserByUsername(@PathVariable String username) {
        Utente user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/assign-role")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable Long id, @RequestParam String roleType) {
        userService.assignRoleToUser(id, roleType);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/remove-role")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long id, @RequestParam String roleType) {
        userService.removeRoleFromUser(id, roleType);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/by-role")
    public ResponseEntity<List<Utente>> getUsersByRole(@RequestParam String roleType) {
        List<Utente> users = userService.findUtenteByRuolo(roleType);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Utente> updateUser(@PathVariable Long id, @RequestBody UtenteDTO userDTO) {
        Utente updatedUser = userService.updateUser(id, userDTO);
        //Utente uploadImage = userService.uploadFotoProfilo();
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{clienteId}/invia-email")
    public void sendEmailToCliente(@PathVariable Long clienteId,
                                   @RequestParam String subject,
                                   @RequestParam String message) {
        clienteService.sendEmailToCliente(clienteId, subject, message);
    }

    @PatchMapping("/{utenteId}/avatar")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String addLogo(@PathVariable("utenteId") Long utenteId, @RequestParam("avatar") MultipartFile file) {
        return this.userService.uploadAvatar(file, utenteId);
    }
}