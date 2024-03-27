package mx.edu.utez.mexprotec.services.category;


import mx.edu.utez.mexprotec.models.animals.typePet.TypePet;
import mx.edu.utez.mexprotec.models.animals.typePet.TypePetRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TypePetService {

    @Autowired
    private TypePetRepository typeRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<TypePet>> getAll(){
        return new CustomResponse<>(
                this.typeRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<TypePet> getOne(Long id){
        Optional<TypePet> optional = this.typeRepository.findById(id);
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
    public CustomResponse<TypePet> insert(TypePet type){
        return new CustomResponse<>(
                this.typeRepository.saveAndFlush(type),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<TypePet> update(TypePet type){
        if(!this.typeRepository.existsById(type.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.typeRepository.saveAndFlush(type),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    // Eliminar una categoría por ID
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> deleteById(Long id) {
        if (!this.typeRepository.existsById(id)) {
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }

        this.typeRepository.deleteById(id);
        return new CustomResponse<>(
                true,
                false,
                200,
                "Tipo de animal eliminado correctamente"
        );
    }
}
