package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.RaceDto;
import mx.edu.utez.mexprotec.models.animals.race.Race;
import mx.edu.utez.mexprotec.services.RaceService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/race/")
@CrossOrigin(origins = {"*"})
public class RaceController {

    @Autowired
    private RaceService raceService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Race>>> getAll() {
        return new ResponseEntity<>(
                this.raceService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Race>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.raceService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Race>> insert(
            @RequestBody RaceDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.raceService.insert(dto.getRace()),
                HttpStatus.CREATED
        );
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Race>> update(
            @RequestBody RaceDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.raceService.update(dto.getRace()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.raceService.deleteById(id),
                HttpStatus.OK
        );
    }

}
