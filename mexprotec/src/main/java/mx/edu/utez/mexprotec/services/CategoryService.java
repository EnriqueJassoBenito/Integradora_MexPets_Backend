package mx.edu.utez.mexprotec.services;

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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Category>> getAll(){
        return new CustomResponse<>(
                this.categoryRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los activos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Category>> getAllActive(){
        return new CustomResponse<>(
                this.categoryRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    ///Servicio para los inactivos
    @Transactional(readOnly = true)
    public  CustomResponse<List<Category>> getAllInactive(){
        return new CustomResponse<>(
                this.categoryRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    ///Horario por id
    @Transactional(readOnly = true)
    public CustomResponse<Category> getOne(Long id){
        Optional<Category> optional = this.categoryRepository.findById(id);
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
    public CustomResponse<Category> insert(Category category){
        return new CustomResponse<>(
                this.categoryRepository.saveAndFlush(category),
                false,
                200,
                "Registrado correctamente"
        );
    }

    //Actualizar un horario
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Category> update(Category category){
        if(!this.categoryRepository.existsById(category.getId()))
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "No encontrado"
            );
        return new CustomResponse<>(
                this.categoryRepository.saveAndFlush(category),
                false,
                200,
                "Actualizado correctamente"
        );
    }

    //Cambiar Status
    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Category category){
        if(!this.categoryRepository.existsById(category.getId())){
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }
        return new CustomResponse<>(
                this.categoryRepository.updateStatusById(
                        category.getStatus(), category.getId()
                ) == 1,
                false,
                200,
                "¡Se ha cambiado el status correctamente!"
        );
    }

    // Eliminar una categoría por ID
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> deleteById(Long id) {
        if (!this.categoryRepository.existsById(id)) {
            return new CustomResponse<>(
                    false,
                    true,
                    400,
                    "No encontrado"
            );
        }

        this.categoryRepository.deleteById(id);

        return new CustomResponse<>(
                true,
                false,
                200,
                "Categoría eliminada correctamente"
        );
    }
}
