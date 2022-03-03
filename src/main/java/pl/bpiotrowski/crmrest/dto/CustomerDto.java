package pl.bpiotrowski.crmrest.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CustomerDto {

    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

}
