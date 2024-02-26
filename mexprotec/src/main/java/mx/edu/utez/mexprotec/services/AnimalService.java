package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.animals.AnimalsRepository;
import mx.edu.utez.mexprotec.models.category.Category;
import mx.edu.utez.mexprotec.models.category.CategoryRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalsRepository animalsRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Animals>> getAll(){
        return new CustomResponse<>(
                this.animalsRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Animals>> getAllActive(){
        return new CustomResponse<>(
                this.animalsRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Animals>> getAllInactive(){
        return new CustomResponse<>(
                this.animalsRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///Horario por id
    @Transactional(readOnly = true)
    public CustomResponse<Animals> getOne(Long id){
        Optional<Animals> optional = this.animalsRepository.findByIdAnimal(id);
        if (optional.isPresent()){
            return new CustomResponse<>(
                    optional.get(),
                    false,
                    200,
                    "Ok"
            );
        }else {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        }
    }

    //Insertar un horario
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Animals> insert(Animals animal){
        return new CustomResponse<>(
                this.animalsRepository.saveAndFlush(animal),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar un horario
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Animals> update(Animals animal){
        if(!this.animalsRepository.existsById(animal.getIdAnimal()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.animalsRepository.saveAndFlush(animal),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Animals animals){
        if(!this.animalsRepository.existsById(animals.getIdAnimal())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        return new CustomResponse<>(
                this.animalsRepository.updateStatusById(
                        animals.getStatus(), animals.getIdAnimal()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status correctamente!"
        );
    }
}
