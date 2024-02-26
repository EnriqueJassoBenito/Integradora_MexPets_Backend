package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.CategoryDto;
import mx.edu.utez.mexprotec.models.category.Category;
import mx.edu.utez.mexprotec.services.CategoryService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
@CrossOrigin(origins = {"*"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Category>>> getAll() {
        return new ResponseEntity<>(
                this.categoryService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<Category>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.categoryService.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Category>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.categoryService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Category>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.categoryService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar un horario
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Category>> insert(
            @RequestBody CategoryDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.categoryService.insert(dto.getCategory()),
                HttpStatus.CREATED
        );
    }

    //Modificar un horario
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Category>> update(
            @RequestBody CategoryDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.categoryService.update(dto.getCategory()),
                HttpStatus.CREATED
        );
    }

    //Modificar el status de una categoría
    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody CategoryDto dto) {
        return new ResponseEntity<>(
                this.categoryService.changeStatus(dto.getCategory()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.categoryService.deleteById(id),
                HttpStatus.OK
        );
    }
}
