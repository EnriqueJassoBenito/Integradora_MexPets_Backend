package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.AnimalDto;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.animals.ApprovalStatus;
import mx.edu.utez.mexprotec.services.AnimalService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /*@GetMapping("/getActive")
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
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Animals>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.animalService.getOne(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/pendientes")
    public ResponseEntity<CustomResponse<List<Animals>>> getPendingApprovalAnimals() {
        CustomResponse<List<Animals>> response = animalService.getPendingApprovalAnimals();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<CustomResponse<Animals>> insert(
            @ModelAttribute AnimalDto dto,
            @RequestParam("imageFiles") List<MultipartFile> imageFiles,
            @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.animalService.insert(dto.toAnimals(), imageFiles),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Animals>> update(
            @PathVariable("id") Long id,
            @RequestBody AnimalDto dto,
            @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        dto.setId(id);
        return new ResponseEntity<>(
                this.animalService.update(dto.toAnimals()),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}/aprobacion")
    public ResponseEntity<CustomResponse<String>> approveOrRejectAnimal(@PathVariable Long id,
                                                                        @RequestParam ApprovalStatus approvalStatus,
                                                                        @RequestParam String moderatorComment) {
        CustomResponse<Boolean> response = animalService.approveOrRejectAnimal(id, approvalStatus, moderatorComment);
        String message;
        HttpStatus httpStatus;

        if (response.getData() != null && response.getData()) {
            message = "Animal aprobado correctamente";
            httpStatus = HttpStatus.OK;
        } else {
            message = "Animal rechazado o no encontrado";
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(new CustomResponse<>(message, false, httpStatus.value(), message), httpStatus);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> delete(
            @PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.animalService.delete(id),
                HttpStatus.OK
        );
    }
}
