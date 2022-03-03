package pl.bpiotrowski.crmrest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bpiotrowski.crmrest.dto.CustomerDto;
import pl.bpiotrowski.crmrest.entity.Customer;
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
        Customer entity = customerMapper.map(dto);
        Customer savedEntity = customerRepository.save(entity);
        return customerMapper.map(savedEntity);
    }

    public CustomerDto update(CustomerDto dto) {
        Customer updatedCustomer = customerRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomerNotFoundException(dto.getId()));
        updatedCustomer.setName(dto.getName());
        customerRepository.save(updatedCustomer);
        return customerMapper.map(updatedCustomer);
    }

    public void delete(Long id) {
        Customer task = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(task);
    }


}
