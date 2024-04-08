package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.logs.Logs;
import mx.edu.utez.mexprotec.models.logs.LogsRepository;
import mx.edu.utez.mexprotec.models.users.Users;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LogsService {

    @Autowired
    private LogsRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<Logs> getById(String id) throws SQLException {
        return new CustomResponse<>(
                this.repository.findById(id).orElse(null),
                false,
                200,
                "Correcto funcionando");
    }
    @Transactional(readOnly = true)
    public CustomResponse<List<Logs>> getAll() throws SQLException {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Correcto funcionando");
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Logs> save(Logs logs) throws SQLException {
        return new CustomResponse<>(this.repository.save(logs),
                false,
                200,
                "Correcto funcionando");
    }

    public void logAction(String actionType, String details) {
        Logs log = new Logs();
        log.setActionType(actionType);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        repository.save(log);
    }
}
