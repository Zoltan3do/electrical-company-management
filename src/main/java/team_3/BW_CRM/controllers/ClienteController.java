package team_3.BW_CRM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team_3.BW_CRM.entities.Cliente;
import team_3.BW_CRM.exceptions.BadRequestException;
import team_3.BW_CRM.payloads.ClienteDTO;
import team_3.BW_CRM.services.ClienteService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


//    @GetMapping
//    public Page<Cliente> getCliente(@RequestParam(defaultValue = "0") int page,
//                                    @RequestParam(defaultValue = "10") int size,
//                                    @RequestParam(defaultValue = "id") String sortBy) {
//        return clienteService.getAllClienteList(page, size, sortBy);
//    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @GetMapping()
    public Page<Cliente> searchClientes(@RequestParam(required = false) Double fatturatoMinimo,
                                        @RequestParam(required = false) LocalDate dataInserimento,
                                        @RequestParam(required = false) LocalDate dataUltimoContatto,
                                        @RequestParam(required = false) String parteDelNome,
                                        @RequestParam(required = false) String nomeContatto,
                                        @RequestParam(required = false) String cognomeContatto,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataInserimento").descending());
        return clienteService.findByCriteria(fatturatoMinimo, dataInserimento, dataUltimoContatto, parteDelNome, nomeContatto, cognomeContatto, pageable);
    }


    @PostMapping("/{clienteId}/invia-email")
    public void sendEmailToCliente(@PathVariable Long clienteId,
                                   @RequestParam String subject,
                                   @RequestParam String message) {
        clienteService.sendEmailToCliente(clienteId, subject, message);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public Cliente save(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.clienteService.save(body);
    }

    @PatchMapping("/{clienteId}/logo")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String addLogo(@PathVariable("clienteId") Long clienteId, @RequestParam("logo") MultipartFile file){
        return this.clienteService.uploadLogoAziendale( file, clienteId);
    }
}
