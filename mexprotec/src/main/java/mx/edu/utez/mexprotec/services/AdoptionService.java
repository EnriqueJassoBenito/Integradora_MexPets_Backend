package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.config.service.CloudinaryService;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.adoption.AdoptionRepository;
import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImage;
import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImageRepository;
import mx.edu.utez.mexprotec.services.imageCloudy.AdoptionLimitService;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private AdoptionImageRepository adoptionImageRepository;

    @Autowired
    private AdoptionLimitService adoptionLimitService;

    @Transactional(readOnly = true)
    public CustomResponse<List<Adoption>> getAll(){
        return new CustomResponse<>(
                this.adoptionRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Adoption>> getPendingAdoptions() {
        List<Adoption> pendingAdoptions = adoptionRepository.findAllByStatus(false);
        return new CustomResponse<>(pendingAdoptions, false, 200, "Solicitudes de adopción pendientes recuperadas correctamente");
    }


    @Transactional(readOnly = true)
    public  CustomResponse<List<Adoption>> getAllActive(){
        return new CustomResponse<>(
                this.adoptionRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public  CustomResponse<List<Adoption>> getAllInactive(){
        return new CustomResponse<>(
                this.adoptionRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

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
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Adoption> insert(Adoption adoption, List<MultipartFile> imageFiles) {
        if (adoptionLimitService.isAdoptionLimitReached()) {
            return new CustomResponse<>(null, true, 400, "Se ha alcanzado el límite de adopciones por día");
        }
        Adoption savedAdoption = adoptionRepository.saveAndFlush(adoption);
        List<AdoptionImage> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            String imageUrl = cloudinaryService.uploadFile(file, "adoption_images");
            AdoptionImage adoptionImage = new AdoptionImage();
            adoptionImage.setAdoption(savedAdoption);
            adoptionImage.setImageUrl(imageUrl);
            images.add(adoptionImage);
        }
        savedAdoption.setImages(images);
        adoptionImageRepository.saveAll(images);
        return new CustomResponse<>(savedAdoption, false, 200, "Adopción registrada correctamente");
    }

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
