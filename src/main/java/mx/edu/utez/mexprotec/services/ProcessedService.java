package mx.edu.utez.mexprotec.services;

import jakarta.mail.MessagingException;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.adoption.AdoptionRepository;
import mx.edu.utez.mexprotec.models.processed.Processed;
import mx.edu.utez.mexprotec.models.processed.ProcessedRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import mx.edu.utez.mexprotec.utils.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessedService {

    @Autowired
    private ProcessedRepository processedRepository;

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private Mailer mailer;

    @Transactional(readOnly = true)
    public CustomResponse<List<Processed>> getAll(){
        return new CustomResponse<>(
                this.processedRepository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public  CustomResponse<List<Processed>> getAllActive(){
        return new CustomResponse<>(
                this.processedRepository.findAllByStatus(true),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public  CustomResponse<List<Processed>> getAllInactive(){
        return new CustomResponse<>(
                this.processedRepository.findAllByStatus(false),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<Processed> getOne(UUID id){
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

    @Transactional(rollbackFor =  {SQLException.class})
    public CustomResponse<Processed> insert(Processed processed){
        return new CustomResponse<>(
                this.processedRepository.saveAndFlush(processed),
                false,
                200,
                "Registrado correctamente"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> acceptAdoption(UUID adoptionId) {
        Optional<Adoption> optionalAdoption = adoptionRepository.findById(adoptionId);
        if (optionalAdoption.isPresent()) {
            Adoption adoption = optionalAdoption.get();
            adoption.setStatus(true);
            adoptionRepository.save(adoption);

            Processed processed = new Processed();
            processed.setAdoption(adoption);
            processed.setStatus(true);
            processedRepository.save(processed);

            // Envíar correo electrónico al solicitante informando la aceptación
            try {
                mailer.sendAcceptedRequest(adoption.getCliente().getEmail(), adoption.getCliente().getName());
            } catch (MessagingException e) {
                e.printStackTrace();
                return new CustomResponse<>(false, true, 500, "Error al enviar el correo electrónico de aceptación");
            }

            return new CustomResponse<>(true, false, 200, "Solicitud de adopción aceptada correctamente");
        } else {
            return new CustomResponse<>(false, true, 404, "Solicitud de adopción no encontrada");
        }
    }

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

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> deleteById(UUID id) {
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
