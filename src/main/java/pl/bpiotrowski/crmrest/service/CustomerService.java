package pl.bpiotrowski.crmrest.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.bpiotrowski.crmrest.dto.CustomerDto;
import pl.bpiotrowski.crmrest.entity.Customer;
import pl.bpiotrowski.crmrest.exception.CustomerAlreadyExistsException;
import pl.bpiotrowski.crmrest.exception.CustomerNotFoundException;
import pl.bpiotrowski.crmrest.mapper.CustomerMapper;
import pl.bpiotrowski.crmrest.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public List<CustomerDto> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).getContent().stream()
                .map(customerMapper::map)
                .collect(Collectors.toList());
    }

    public CustomerDto find(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::map)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public CustomerDto create(CustomerDto dto) {
        if (!customerExists(dto.getName())) {
            throw new CustomerAlreadyExistsException(dto.getName());
        }
        Customer entity = customerMapper.map(dto);
        Customer savedEntity = customerRepository.save(entity);
        return customerMapper.map(savedEntity);
    }

    public CustomerDto update(CustomerDto dto) {
        Customer updatedCustomer = customerRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.getId()));
        updatedCustomer.setName(dto.getName());
        updatedCustomer.setFirstField(dto.getFirstField());
        updatedCustomer.setSecondField(dto.getSecondField());
        updatedCustomer.setThirdField(dto.getThirdField());
        updatedCustomer.setFourthField(dto.getFourthField());
        updatedCustomer.setTextArea(dto.getTextArea());
        customerRepository.save(updatedCustomer);
        return customerMapper.map(updatedCustomer);
    }

    public void delete(Long id) {
        Customer task = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(task);
    }

    private boolean customerExists(String name) {
        return customerRepository.findCustomerByName(name) == null;
    }


}
