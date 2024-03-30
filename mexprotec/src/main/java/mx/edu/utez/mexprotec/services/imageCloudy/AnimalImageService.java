package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.config.service.CloudinaryService;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.image.animal.AnimalImage;
import mx.edu.utez.mexprotec.models.image.animal.AnimalImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AnimalImageService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private AnimalImageRepository animalImageRepository;

    public AnimalImage uploadImage(MultipartFile file, Animals animal) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file, "animal_images");
            AnimalImage animalImage = new AnimalImage();
            animalImage.setAnimal(animal);
            animalImage.setImageUrl(imageUrl);
            return animalImageRepository.save(animalImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Otros métodos para gestionar imágenes de animales
}
