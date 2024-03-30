package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.config.service.CloudinaryService;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImage;
import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdoptionImageService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private AdoptionImageRepository adoptionImageRepository;

    public AdoptionImage uploadImage(MultipartFile file, Adoption adoption) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file, "adoption_images");
            AdoptionImage adoptionImage = new AdoptionImage();
            adoptionImage.setAdoption(adoption);
            adoptionImage.setImageUrl(imageUrl);
            return adoptionImageRepository.save(adoptionImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Otros métodos para gestionar imágenes de adopciones
}
