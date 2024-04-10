package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.AdoptionDto;

import mx.edu.utez.mexprotec.models.adoption.Adoption;

import mx.edu.utez.mexprotec.services.AdoptionService;

import mx.edu.utez.mexprotec.services.LogsService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/adoption/")
@CrossOrigin(origins = {"*"})
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

    @Autowired
    private LogsService logsService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Adoption>>> getAll() {
        return new ResponseEntity<>(
                this.adoptionService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<Adoption>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.adoptionService.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Adoption>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.adoptionService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Adoption>> getOne(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                this.adoptionService.getOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Adoption>> insert(
            @ModelAttribute AdoptionDto dto,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
            @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.adoptionService.insert(dto.toAdoption(), imageFiles),
                HttpStatus.CREATED
        );
    }
    /*@PostMapping("/")
    public ResponseEntity<CustomResponse<Adoption>> insert(
            @ModelAttribute AdoptionDto dto,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
            @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        CustomResponse<Adoption> response = this.adoptionService.insert(dto.toAdoption(), imageFiles);
        if (response != null && response.getData() != null) {
            String action = "INSERT_ADOPTION";
            String details = "Adopción insertada: " + response.getData().getId();
            this.logsService.logAction(action, details);
        }
        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Adoption>> update(
            @PathVariable("id") UUID id,
            @RequestBody AdoptionDto dto,
            @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        dto.setId(id);
        return new ResponseEntity<>(
                this.adoptionService.update(dto.toAdoption()),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @PathVariable("id") UUID id,
            @RequestBody AdoptionDto dto) {
        dto.setId(id);
        return new ResponseEntity<>(
                this.adoptionService.changeStatus(dto.toAdoption()),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                this.adoptionService.delete(id),
                HttpStatus.OK
        );
    }
}
