package pl.bpiotrowski.crmrest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomerDto {

    private Long id;

    @NotNull
    @Size(min = 3, message = "The name must consist of at least 3 characters")
    private String name;

    private String firstField;

    private String secondField;

    private String thirdField;

    private String fourthField;

    @Size(max = 2000, message = "The text area must consist of less than 2000 characters")
    private String textArea;

}
