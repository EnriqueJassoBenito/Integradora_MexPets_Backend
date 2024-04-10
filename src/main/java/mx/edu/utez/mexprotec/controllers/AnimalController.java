package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.AnimalDto;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.animals.ApprovalStatus;
import mx.edu.utez.mexprotec.models.animals.personality.Personality;
import mx.edu.utez.mexprotec.models.animals.race.Race;
import mx.edu.utez.mexprotec.models.animals.typePet.TypePet;
import mx.edu.utez.mexprotec.services.AnimalService;
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
@RequestMapping("/api/animals/")
@CrossOrigin(origins = {"*"})
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private LogsService logsService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Animals>>> getAll() {
        return new ResponseEntity<>(
                this.animalService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Animals>> getOne(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                this.animalService.getOne(id),
                HttpStatus.OK
        );
    }

    /**/
    @GetMapping("/type/{typePetId}")
    public ResponseEntity<CustomResponse<List<Animals>>> getAnimalsByTypePet(@PathVariable UUID typePetId) {
        TypePet typePet = new TypePet();
        typePet.setId(typePetId);

        CustomResponse<List<Animals>> response = this.animalService.getAnimalsByTypePet(typePet);
        if (response.getError()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/race/{raceId}")
    public ResponseEntity<CustomResponse<List<Animals>>> getAnimalsByRace(@PathVariable UUID raceId) {
        Race race = new Race();
        race.setId(raceId);

        CustomResponse<List<Animals>> response = this.animalService.getAnimalsByRace(race);
        if (response.getError()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/personality/{personalityId}")
    public ResponseEntity<CustomResponse<List<Animals>>> getAnimalsByPersonality(@PathVariable UUID personalityId) {
        Personality personality = new Personality();
        personality.setId(personalityId);

        CustomResponse<List<Animals>> response = this.animalService.getAnimalsByPersonality(personality);
        if (response.getError()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/female")
    public ResponseEntity<CustomResponse<List<Animals>>> getFemaleAnimals() {
        CustomResponse<List<Animals>> response = this.animalService.getFemaleAnimals();
        if (response.getError()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/male")
    public ResponseEntity<CustomResponse<List<Animals>>> getMaleAnimals() {
        CustomResponse<List<Animals>> response = this.animalService.getMaleAnimals();
        if (response.getError()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**/
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

    /*@PostMapping("/")
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

        CustomResponse<Animals> response = this.animalService.insert(dto.toAnimals(), imageFiles);

        if (response != null && response.getData() != null) {
            String action = "INSERT_ANIMAL";
            String details = "Animal insertado: " + response.getData().getId();
            this.logsService.logAction(action, details);
        }

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }*/


    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Animals>> update(
            @PathVariable("id") UUID id,
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
    public ResponseEntity<CustomResponse<String>> approveOrRejectAnimal(@PathVariable UUID id,
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
            @PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                this.animalService.delete(id),
                HttpStatus.OK
        );
    }
}
