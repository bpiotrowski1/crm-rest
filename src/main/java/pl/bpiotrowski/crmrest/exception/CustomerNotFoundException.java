package pl.bpiotrowski.crmrest.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("Customer id: " + id + " not found");
    }

}
