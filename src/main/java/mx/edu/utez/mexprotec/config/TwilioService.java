package mx.edu.utez.mexprotec.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*@Service
@Transactional
public class TwilioService {
    @Value("${TWILIO_ACCOUNT_SID}")
    String sid;
    @Value("${TWILIO_AUTH_TOKEN}")
    String token;
    @Value("${TWILIO_NUMBER}")
    String phoneNumber;
    public Boolean sendSMS(String number, String message) {
        try {
            Twilio.init(sid, token);
            Message.creator(
                    new PhoneNumber("" + number),
                    new PhoneNumber(phoneNumber),
                    message
            ).create();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}*/
