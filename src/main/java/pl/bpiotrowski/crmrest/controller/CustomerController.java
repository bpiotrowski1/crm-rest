package pl.bpiotrowski.crmrest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.bpiotrowski.crmrest.dto.CustomerDto;
import pl.bpiotrowski.crmrest.service.CustomerService;

import javax.validation.Valid;
import java.util.List;

import static pl.bpiotrowski.crmrest.constants.Statics.PAGE_SIZE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> findAll(@SortDefault(sort = "name") @PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        return customerService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CustomerDto findCustomer(@PathVariable Long id) {
        return customerService.find(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto addCustomer(@Valid @RequestBody CustomerDto customer) {
        return customerService.create(customer);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@Valid @RequestBody CustomerDto customer, @PathVariable Long id) {
        customer.setId(id);
        return customerService.update(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }


}
