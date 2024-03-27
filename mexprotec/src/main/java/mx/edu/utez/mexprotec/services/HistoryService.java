package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.history.History;
import mx.edu.utez.mexprotec.models.history.HistoryRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<History>> getAll(){
        return new CustomResponse<>(
                this.historyRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<History>> getAllActive(){
        return new CustomResponse<>(
                this.historyRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<History>> getAllInactive(){
        return new CustomResponse<>(
                this.historyRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///Id
    @Transactional(readOnly = true)
    public CustomResponse<History> getOne(Long id){
        Optional<History> optional = this.historyRepository.findById(id);
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
    public CustomResponse<History> insert(History history){
        return new CustomResponse<>(
                this.historyRepository.saveAndFlush(history),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<History> update(History history){
        if(!this.historyRepository.existsById(history.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.historyRepository.saveAndFlush(history),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(History history){
        if(!this.historyRepository.existsById(history.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        return new CustomResponse<>(
                this.historyRepository.updateStatusById(
                        history.getStatus(), history.getId()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status correctamente!"
        );
    }

    ///Eliminar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> delete(Long id){
        if(!this.historyRepository.existsById(id)){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        this.historyRepository.deleteById(id);
        return new CustomResponse<>(
                true,
                false,
                200,
                "Eliminado correctamente"
        );
    }
}
