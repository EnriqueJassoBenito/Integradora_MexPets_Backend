package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.AnimalDto;
import mx.edu.utez.mexprotec.dtos.CategoryDto;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.category.Category;
import mx.edu.utez.mexprotec.services.AnimalService;
import mx.edu.utez.mexprotec.services.CategoryService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals/")
@CrossOrigin(origins = {"*"})
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Animals>>> getAll() {
        return new ResponseEntity<>(
                this.animalService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<Animals>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.animalService.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Animals>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.animalService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Animals>> getOne(@PathVariable("id_animal") Long id) {
        return new ResponseEntity<>(
                this.animalService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Animals>> insert(
            @RequestBody AnimalDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.animalService.insert(dto.getAdnimals()),
                HttpStatus.CREATED
        );
    }

    //Modificar un horario
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Animals>> update(
            @RequestBody AnimalDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.animalService.update(dto.getAdnimals()),
                HttpStatus.CREATED
        );
    }

    //Modificar el status de una categoría
    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody AnimalDto dto) {
        return new ResponseEntity<>(
                this.animalService.changeStatus(dto.getAdnimals()),
                HttpStatus.OK
        );
    }
}
