package mx.edu.utez.mexprotec.captchaservice.dto;

import java.util.List;

public class CaptchaResponse {
    private boolean success;
    private List<String> errors;

    public CaptchaResponse() {
    }

    public CaptchaResponse(boolean success, List<String> errors) {
        this.success = success;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
