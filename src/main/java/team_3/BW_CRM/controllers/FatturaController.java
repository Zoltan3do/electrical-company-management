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
import team_3.BW_CRM.entities.Fattura;
import team_3.BW_CRM.exceptions.BadRequestException;
import team_3.BW_CRM.payloads.NewFatturaDTO;
import team_3.BW_CRM.services.FatturaService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fatture")

public class FatturaController {
    @Autowired
    private FatturaService fatturaService;


    @GetMapping
    public Page<Fattura> getFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) Double minImporto,
            @RequestParam(required = false) Double maxImporto) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return fatturaService.findWithFilters(clienteId, data, anno, minImporto, maxImporto, pageable);
    }


    @GetMapping("/clienti/{id}/fatture")
    public Page<Fattura> getFattureByIdCliente(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.fatturaService.findByIdCliente(id, pageable);
    }


    @GetMapping("/data")
    public Page<Fattura> getFattureByData(
            @RequestParam LocalDate data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.fatturaService.findByData(data, pageable);
    }

    @GetMapping("/anno")
    public Page<Fattura> getFattureByAnno(
            @RequestParam Integer anno,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.fatturaService.findByYear(anno, pageable);
    }

    @GetMapping("/{fatturaNumero}")
    public Fattura getFatturaByNumero(
            @PathVariable Integer fatturaNumero) {
        return this.fatturaService.findByNumero(fatturaNumero);
    }


    @PutMapping("/num/{fatturaNumero}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura findFatturaByNumeroAndUpdate(
            @PathVariable Integer fatturaNumero,
            @RequestBody @Validated NewFatturaDTO body,
            BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nei dati inviati: " + message);
        }

        return fatturaService.findByNumeroAndUpdate(fatturaNumero, body);
    }


   @DeleteMapping("/num/{fatturaNumero}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @PreAuthorize("hasAuthority('ADMIN')")
   public void findFatturaByNumeroAndDelete(@PathVariable Integer numero) {
        this.fatturaService.findByNumeroAndDelete(numero);
   }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public Fattura saveFattura(@RequestBody @Validated NewFatturaDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            String message = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Payload error: " + message);
        }
        return this.fatturaService.createFattura(body);
    }

    @GetMapping("/{fatturaId}")
    public Fattura findByIdFattura(@PathVariable Long fatturaId) {
        return this.fatturaService.findById(fatturaId);
    }

    @PutMapping("/id/{fatturaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura findByIdAndUpdate(
            @PathVariable Long fatturaId,
            @RequestBody @Validated NewFatturaDTO body,
            BindingResult validationResult) {
        if (validationResult.hasErrors()) {String message = validationResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload: " + message);
        }
        return this.fatturaService.findByIdAndUpdate(fatturaId, body);
    }

    @DeleteMapping("/id/{fatturaId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable Long fatturaId) {
        this.fatturaService.findByIdAndDelete(fatturaId);
    }

    @GetMapping("/importo")
    public Page<Fattura> getFattureByImporto(
            @RequestParam(required = false) Double minImporto,
            @RequestParam(required = false) Double maxImporto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.fatturaService.findByRange(minImporto, maxImporto, pageable);
    }

}