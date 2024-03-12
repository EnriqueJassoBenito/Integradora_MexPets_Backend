package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.PersonalityDto;
import mx.edu.utez.mexprotec.models.animals.personality.Personality;
import mx.edu.utez.mexprotec.services.PersonalityService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personality/")
@CrossOrigin(origins = {"*"})
public class PersonalityController {

    @Autowired
    private PersonalityService personalityService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Personality>>> getAll() {
        return new ResponseEntity<>(
                this.personalityService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Personality>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.personalityService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Personality>> insert(
            @RequestBody PersonalityDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.personalityService.insert(dto.getPersonality()),
                HttpStatus.CREATED
        );
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Personality>> update(
            @RequestBody PersonalityDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.personalityService.update(dto.getPersonality()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.personalityService.deleteById(id),
                HttpStatus.OK
        );
    }
}
