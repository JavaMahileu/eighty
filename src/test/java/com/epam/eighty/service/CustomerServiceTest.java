package com.epam.eighty.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.conversion.Result;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.repository.CustomerRepository;
import com.epam.eighty.service.impl.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    
    private static final long QUESTIONS_NUMBER = 9999L;

    private Topic root;
    private List<Customer> customers;
    private Result<Customer> results;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Customer customer1 = new Customer();
        customer1.setId(1001L);
        customer1.setName("fake1");
        customer1.setCount(1L);
        Customer customer2 = new Customer();
        customer2.setId(1002L);
        customer2.setName("fake2");
        customer2.setCount(1L);
        Customer customer3 = new Customer();
        customer3.setId(1003L);
        customer3.setName("fake3");

        root = new Topic();
        root.setId(0L);

        customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        List<Customer> fakeCustomers = new ArrayList<>();
        fakeCustomers.add(customer1);
        fakeCustomers.add(customer2);
        fakeCustomers.add(customer3);

        results = new QueryResultBuilder<>(fakeCustomers);
    }

    @Test
    public void test_getCustomersMatchingName() {
        Mockito.when(customerRepository.getCustomersMatchingName(Mockito.anyString())).thenReturn(customers);

        List<Customer> actualCustomers = customerService.getCustomersMatchingName(Mockito.anyString());

        assertEquals(actualCustomers, customers);
    }

    @Test
    public void test_getCustomersByTopicId() {
        when(customerRepository.getCustomersByTopicId(root.getId())).thenReturn(customers);
        when(customerRepository.getQuestionsNumberInTopicByCustomer(Mockito.anyString(), Mockito.anyLong())).thenReturn(QUESTIONS_NUMBER);

        List<Customer> actualCustomers = customerService.getCustomersByTopicId(root.getId());

        assertEquals(actualCustomers, customers);
        actualCustomers.forEach(customer -> assertEquals(QUESTIONS_NUMBER, customer.getCountInTopic().longValue()));
    }

    @Test
    public void test_getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(results);

        List<Customer> actualCustomers = customerService.getAllCustomers();

        assertEquals(actualCustomers, customers);
    }
}
