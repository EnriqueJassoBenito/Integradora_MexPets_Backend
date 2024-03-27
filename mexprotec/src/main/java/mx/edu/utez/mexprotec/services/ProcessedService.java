package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.processed.Processed;
import mx.edu.utez.mexprotec.models.processed.ProcessedRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessedService {

    @Autowired
    private ProcessedRepository processedRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Processed>> getAll(){
        return new CustomResponse<>(
                this.processedRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Processed>> getAllActive(){
        return new CustomResponse<>(
                this.processedRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Processed>> getAllInactive(){
        return new CustomResponse<>(
                this.processedRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///Id
    @Transactional(readOnly = true)
    public CustomResponse<Processed> getOne(Long id){
        Optional<Processed> optional = this.processedRepository.findById(id);
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
    public CustomResponse<Processed> insert(Processed processed){
        return new CustomResponse<>(
                this.processedRepository.saveAndFlush(processed),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Processed> update(Processed processed){
        if(!this.processedRepository.existsById(processed.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.processedRepository.saveAndFlush(processed),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Processed processed){
        if(!this.processedRepository.existsById(processed.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        return new CustomResponse<>(
                this.processedRepository.updateStatusById(
                        processed.getStatus(), processed.getId()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status correctamente!"
        );
    }

    // Eliminar
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> deleteById(Long id) {
        if (!this.processedRepository.existsById(id)) {
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }

        this.processedRepository.deleteById(id);

        return new CustomResponse<>(
                true,
                false,
                200,
                "Eliminado correctamente"
        );
    }
}
