package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.CategoryDto;
import mx.edu.utez.mexprotec.dtos.UserDto;
import mx.edu.utez.mexprotec.models.category.Category;
import mx.edu.utez.mexprotec.models.users.Users;
import mx.edu.utez.mexprotec.services.UserService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = {"*"})
public class UserController {

    @Autowired
    private UserService usuarioService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Users>>> getAll(){
        return new ResponseEntity<>(
                this.usuarioService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Users>> getById(
            @PathVariable("id")
            Integer id){
        return new ResponseEntity<>(
                this.usuarioService.getOne(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<CustomResponse<Users>> insert(@RequestBody UserDto usuario) {
        return new ResponseEntity<>(
                this.usuarioService.insert(usuario.getUsers()), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Users>> update(
            @RequestBody UserDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.usuarioService.update(dto.getUsers()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableDisable(
            @PathVariable("id") Integer id
    ){
        return new ResponseEntity<>(
                this.usuarioService.delete(id),
                HttpStatus.OK
        );
    }
}
