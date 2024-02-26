package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.AdoptionDto;

import mx.edu.utez.mexprotec.models.adoption.Adoption;

import mx.edu.utez.mexprotec.services.AdoptionService;

import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoption/")
@CrossOrigin(origins = {"*"})
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;

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
    public ResponseEntity<CustomResponse<Adoption>> getOne(@PathVariable("id_adoption") Long id) {
        return new ResponseEntity<>(
                this.adoptionService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Adoption>> insert(
            @RequestBody AdoptionDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.adoptionService.insert(dto.getAdoption()),
                HttpStatus.CREATED
        );
    }

    //Modificar un horario
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Adoption>> update(
            @RequestBody AdoptionDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.adoptionService.update(dto.getAdoption()),
                HttpStatus.CREATED
        );
    }

    //Modificar el status de una categoría
    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody AdoptionDto dto) {
        return new ResponseEntity<>(
                this.adoptionService.changeStatus(dto.getAdoption()),
                HttpStatus.OK
        );
    }
}
