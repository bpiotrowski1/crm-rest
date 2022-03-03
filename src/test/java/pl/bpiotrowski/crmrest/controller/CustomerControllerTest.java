package pl.bpiotrowski.crmrest.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.bpiotrowski.crmrest.entity.Customer;
import pl.bpiotrowski.crmrest.repository.CustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    public void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    public void getAllTest() throws Exception {
        // given
        String customer1Name = "customer1";
        String customer2Name = "customer2";
        Customer customer1 = create(customer1Name);
        Customer customer2 = create(customer2Name);
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // when
        mockMvc.perform(
                        get("/api/customers?pageIndex=0&pageSize=10&sortDirection=ASC&sortColumn=id")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(customer1Name)))
                .andExpect(jsonPath("$[1].name", is(customer2Name)));
    }

    @Test
    public void getOneTest() throws Exception {
        // given
        String name = "test name";
        Customer customer = create(name);
        Long id = customerRepository.save(customer).getId();

        // when
        mockMvc.perform(get("/api/customers/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Integer.valueOf(String.valueOf(id)))))
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    public void createTest() throws Exception {
        // given
        String customerName = "task name";
        String customerJson = "{\"name\":\"{name}\"}"
                .replace("{name}", customerName);

        // when
        mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(customerJson))
                .andExpect(status().isCreated());

        // then
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);

        Customer result = customers.get(0);
        assertThat(result.getName()).isEqualTo(customerName);
    }

    @Test
    public void createNotValidTest() throws Exception {
        // given
        String customerName = "";
        String customerJson = "{\"name\":\"{name}\"}"
                .replace("{name}", customerName);

        // when
        mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON).content(customerJson))
                .andExpect(status().isBadRequest());

        // then
        List<Customer> result = customerRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    public void updateTest() throws Exception {
        // given
        Customer customer = new Customer();
        customer.setName("test name");

        Long customerId = customerRepository.save(customer).getId();

        String customerName = "new name";
        String customerJson = "{\"id\":{id},\"name\":\"{name}\"}"
                .replace("{id}", String.valueOf(customerId))
                .replace("{name}", customerName);

        // when
        mockMvc.perform(put("/api/customers/{id}", customerId).contentType(MediaType.APPLICATION_JSON).content(customerJson))
                .andExpect(status().isOk());

        // then
        Customer result = customerRepository.findById(customerId).orElseThrow(IllegalArgumentException::new);
        assertThat(result.getName()).isEqualTo(customerName);
    }

    @Test
    public void updateNotValidTest() throws Exception {
        // given
        long customerId = 1L;
        String customerName = "";
        String customerJson = "{\"id\":{id},\"name\":\"{name}\"}"
                .replace("{id}", String.valueOf(customerId))
                .replace("{name}", customerName);

        // when
        mockMvc.perform(put("/api/customers/{id}", customerId).contentType(MediaType.APPLICATION_JSON).content(customerJson))
                .andExpect(status().isBadRequest());

        // then
        List<Customer> result = customerRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    public void deleteTest() throws Exception {
        // given
        String name = "task name";
        Customer customer = create(name);
        Long customerId = customerRepository.save(customer).getId();

        // when
        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isOk());

        // then
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();
    }

    private Customer create(String name) {
        Customer entity = new Customer();
        entity.setName(name);
        return entity;
    }
}

