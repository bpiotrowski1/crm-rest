package pl.bpiotrowski.crmrest.mapper;

import org.mapstruct.Mapper;
import pl.bpiotrowski.crmrest.dto.CustomerDto;
import pl.bpiotrowski.crmrest.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer map(CustomerDto dto);

    CustomerDto map(Customer entity);

}
