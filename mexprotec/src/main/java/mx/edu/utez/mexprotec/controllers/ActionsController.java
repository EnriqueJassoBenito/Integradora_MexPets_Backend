package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.ActionsDto;
import mx.edu.utez.mexprotec.models.action.Actions;
import mx.edu.utez.mexprotec.services.ActionsService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions/")
@CrossOrigin(origins = {"*"})
public class ActionsController {

    @Autowired
    private ActionsService actionsService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Actions>>> getAll() {
        return new ResponseEntity<>(
                this.actionsService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Actions>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.actionsService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Actions>> insert(
            @RequestBody ActionsDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.actionsService.insert(dto.getActions()),
                HttpStatus.CREATED
        );
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Actions>> update(
            @RequestBody ActionsDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.actionsService.update(dto.getActions()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.actionsService.deleteById(id),
                HttpStatus.OK
        );
    }

}
