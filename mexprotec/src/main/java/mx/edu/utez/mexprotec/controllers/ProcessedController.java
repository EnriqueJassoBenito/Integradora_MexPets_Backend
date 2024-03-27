package mx.edu.utez.mexprotec.controllers;

import jakarta.validation.Valid;
import mx.edu.utez.mexprotec.dtos.ProcessedDto;
import mx.edu.utez.mexprotec.models.processed.Processed;
import mx.edu.utez.mexprotec.services.ProcessedService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoption_processed/")
@CrossOrigin(origins = {"*"})
public class ProcessedController {

    @Autowired
    private ProcessedService processedService;

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<Processed>>> getAll() {
        return new ResponseEntity<>(
                this.processedService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getActive")
    public ResponseEntity<CustomResponse<List<Processed>>>
    getAllActive(){
        return new ResponseEntity<>(
                this.processedService.getAllActive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAllInactive")
    public ResponseEntity<CustomResponse<List<Processed>>>
    getAllInactive(){
        return new ResponseEntity<>(
                this.processedService.getAllInactive(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Processed>> getOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.processedService.getOne(id),
                HttpStatus.OK
        );
    }

    //Insertar
    @PostMapping("/")
    public ResponseEntity<CustomResponse<Processed>> insert(
            @RequestBody ProcessedDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.processedService.insert(dto.getProcessed()),
                HttpStatus.CREATED
        );
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Processed>> update(
            @RequestBody ProcessedDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.processedService.update(dto.getProcessed()),
                HttpStatus.CREATED
        );
    }

    //Modificar el status
    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> enableOrDisable(
            @RequestBody ProcessedDto dto) {
        return new ResponseEntity<>(
                this.processedService.changeStatus(dto.getProcessed()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Boolean>> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.processedService.deleteById(id),
                HttpStatus.OK
        );
    }
}
