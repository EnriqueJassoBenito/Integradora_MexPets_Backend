package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.animals.AnimalsRepository;
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

    ///Id
    @Transactional(readOnly = true)
    public CustomResponse<Animals> getOne(Long id){
        Optional<Animals> optional = this.animalsRepository.findById(id);
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

    //Insertar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Animals> insert(Animals animal){
        return new CustomResponse<>(
                this.animalsRepository.saveAndFlush(animal),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Animals> update(Animals animal){
        if(!this.animalsRepository.existsById(animal.getId()))
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
        if(!this.animalsRepository.existsById(animals.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        return new CustomResponse<>(
                this.animalsRepository.updateStatusById(
                        animals.getStatus(), animals.getId()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status correctamente!"
        );
    }

    ///Eliminar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> delete(Long id){
        if(!this.animalsRepository.existsById(id)){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        this.animalsRepository.deleteById(id);
        return new CustomResponse<>(
                true,
                false,
                200,
                "Eliminado correctamente"
        );
    }
}
