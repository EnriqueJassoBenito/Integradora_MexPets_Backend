package mx.edu.utez.mexprotec.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String accountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;

    @Value("${TWILIO_NUMBER}")
    private String twilioNumber;

    public boolean sendSMS(String recipientNumber, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber("+52" + recipientNumber),
                    new PhoneNumber(twilioNumber),
                    messageBody
            ).create();
            System.out.println("Message mexpet: " + message.getSid());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
