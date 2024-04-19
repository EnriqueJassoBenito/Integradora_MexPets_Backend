package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.config.TwilioService;
import mx.edu.utez.mexprotec.dtos.ProcessedDto;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.adoption.AdoptionRepository;
import mx.edu.utez.mexprotec.models.animals.ApprovalStatus;
import mx.edu.utez.mexprotec.models.processed.Processed;
import mx.edu.utez.mexprotec.models.processed.ProcessedRepository;
import mx.edu.utez.mexprotec.models.users.Users;
import mx.edu.utez.mexprotec.models.users.UsersRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
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
    private TwilioService twilioService;

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private UsersRepository usersRepository;

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

    @Transactional(readOnly = true)
    public CustomResponse<List<Processed>> getProcessedAdoptionsByUser(UUID userId) {
        List<Processed> processedAdoptions = processedRepository.findByModeratorId(userId);
        return new CustomResponse<>(processedAdoptions, false, 200, "Adopciones procesadas del usuario obtenidas correctamente");
    }

    @Transactional
    public CustomResponse<Processed> processAdoption(ProcessedDto processedDto) {
        Optional<Adoption> optionalAdoption = adoptionRepository.findById(processedDto.getAdoption().getId());
        if (optionalAdoption.isPresent()) {
            Adoption adoption = optionalAdoption.get();
            if (!adoption.getStatus()) {
                adoption.setStatus(true);
                adoptionRepository.save(adoption);

                Optional<Users> optionalModerator = usersRepository.findById(processedDto.getModerator().getId());
                if (optionalModerator.isPresent()) {
                    Users moderator = optionalModerator.get();

                    Processed processed = processedDto.getProcessed();
                    processed.setAdoption(adoption);
                    processed.setModerator(moderator);
                    Processed savedProcessed = processedRepository.save(processed);
                    return new CustomResponse<>(savedProcessed, false, 200, "Adopción procesada correctamente");
                } else {
                    return new CustomResponse<>(null, true, 404, "No se encontró al moderador con ID " + processedDto.getModerator().getId());
                }
            } else {
                return new CustomResponse<>(null, true, 400, "La adopción ya ha sido gestionada anteriormente");
            }
        } else {
            return new CustomResponse<>(null, true, 404, "No se encontró la adopción con ID " + processedDto.getAdoption().getId());
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

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Processed> updateApprovalStatus(UUID id, ApprovalStatus approvalStatus, String moderatorComment) {
        Optional<Processed> optionalProcessed = processedRepository.findById(id);
        if (optionalProcessed.isPresent()) {
            Processed processed = optionalProcessed.get();
            if (approvalStatus == ApprovalStatus.APPROVED) {
                processed.approve();
                sendApprovalSMS(processed.getAdoption().getAdopter().getPhoneNumber(), "¡Felicidades! Tu adopción ha sido aprobada.");

            } else if (approvalStatus == ApprovalStatus.REJECTED) {
                processed.reject();
            }
            processedRepository.save(processed);
            return new CustomResponse<>(processed, false, 200, "Estado de aprobación actualizado correctamente");
        } else {
            return new CustomResponse<>(null, true, 404, "No se encontró la adopción procesada con ID " + id);
        }
    }


    @Transactional(readOnly = true)
    public CustomResponse<List<Processed>> getByApprovalStatus(ApprovalStatus status) {
        List<Processed> processedList = processedRepository.findByApprovalStatus(status);
        return new CustomResponse<>(processedList, false, 200, "Adopciones procesadas con estado " + status);
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

    private void sendApprovalSMS(String phoneNumber, String message) {
        twilioService.sendSMS(phoneNumber, message);
    }
}
