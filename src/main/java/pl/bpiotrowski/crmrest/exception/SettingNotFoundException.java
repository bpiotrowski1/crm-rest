package pl.bpiotrowski.crmrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SettingNotFoundException extends RuntimeException {

    public SettingNotFoundException(Long id) {
        super("Customer id: " + id + " not found");
    }

}
