package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.adoption.AdoptionRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Adoption>> getAll(){
        return new CustomResponse<>(
                this.adoptionRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Adoption>> getAllActive(){
        return new CustomResponse<>(
                this.adoptionRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Adoption>> getAllInactive(){
        return new CustomResponse<>(
                this.adoptionRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///id
    @Transactional(readOnly = true)
    public CustomResponse<Adoption> getOne(Long id){
        Optional<Adoption> optional = this.adoptionRepository.findById(id);
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
    public CustomResponse<Adoption> insert(Adoption adoption){
        return new CustomResponse<>(
                this.adoptionRepository.saveAndFlush(adoption),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Adoption> update(Adoption adoption){
        if(!this.adoptionRepository.existsById(adoption.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.adoptionRepository.saveAndFlush(adoption),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Adoption adoption){
        if(!this.adoptionRepository.existsById(adoption.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        return new CustomResponse<>(
                this.adoptionRepository.updateStatusById(
                        adoption.getStatus(), adoption.getId()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status correctamente!"
        );
    }

    //Eliminar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> delete(Long id){
        if(!this.adoptionRepository.existsById(id)){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        this.adoptionRepository.deleteById(id);
        return new CustomResponse<>(
                true,
                false,
                200,
                "¡Eliminado correctamente!"
        );
    }
}
