package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.action.Actions;
import mx.edu.utez.mexprotec.models.action.ActionsRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ActionsService {

     @Autowired
    private ActionsRepository actionsRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Actions>> getAll(){
        return new CustomResponse<>(
                this.actionsRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<Actions> getOne(Long id){
        Optional<Actions> optional = this.actionsRepository.findById(id);
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
    public CustomResponse<Actions> insert(Actions actions){
        return new CustomResponse<>(
                this.actionsRepository.saveAndFlush(actions),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Actions> update(Actions actions){
        if(!this.actionsRepository.existsById(actions.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.actionsRepository.saveAndFlush(actions),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    // Eliminar una categoría por ID
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> deleteById(Long id) {
        if (!this.actionsRepository.existsById(id)) {
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }

        this.actionsRepository.deleteById(id);
        return new CustomResponse<>(
                true,
                false,
                200,
                "Action eliminada correctamente"
        );
    }
}
