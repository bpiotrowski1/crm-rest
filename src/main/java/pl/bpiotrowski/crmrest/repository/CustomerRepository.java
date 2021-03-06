package pl.bpiotrowski.crmrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bpiotrowski.crmrest.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByName(String name);

}
