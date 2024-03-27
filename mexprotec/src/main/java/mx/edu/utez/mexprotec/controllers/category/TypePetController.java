package mx.edu.utez.mexprotec.controllers.category;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.category.TypePetDto;
import mx.edu.utez.mexprotec.models.animals.typePet.TypePet;
import mx.edu.utez.mexprotec.services.category.TypePetService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@RestController
@RequestMapping("/api/type-pet/")
@CrossOrigin(origins = {"*"})
public class TypePetController {

    @Autowired
    private TypePetService typeService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<TypePet>>> getAll() {
        return new ResponseEntity<>(
                this.typeService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<TypePet>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.typeService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<TypePet>> insert(
            @RequestBody TypePetDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.typeService.insert(dto.getTypePet()),
                HttpStatus.CREATED
        );
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<TypePet>> update(
            @RequestBody TypePetDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.typeService.update(dto.getTypePet()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.typeService.deleteById(id),
                HttpStatus.OK
        );
    }
}
