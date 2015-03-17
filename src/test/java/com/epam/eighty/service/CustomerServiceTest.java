package com.epam.eighty.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private static final String EMPTY_STRING = "";

    private Customer fake;
    private Topic root;
    private List<Customer> list;
    private List<Customer> fakeList;
    private Result<Customer> fakeResult;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Set<Customer> customers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fake = new Customer();
        fake.setId(10L);
        fake.setName("fake");

        Customer fake1 = new Customer();
        fake1.setId(1001L);
        fake1.setName("fake1");
        fake1.setCount(1L);
        Customer fake2 = new Customer();
        fake2.setId(1002L);
        fake2.setName("fake2");
        fake2.setCount(1L);
        Customer fake3 = new Customer();
        fake3.setId(1003L);
        fake3.setName("fake3");

        customers = new HashSet<>();
        customers.add(fake1);
        customers.add(fake2);

        root = new Topic();
        root.setId(0L);

        list = new ArrayList<>();
        list.add(fake1);
        list.add(fake2);

        fakeList = new ArrayList<>();
        fakeList.add(fake1);
        fakeList.add(fake2);
        fakeList.add(fake3);

        fakeResult = new QueryResultBuilder<>(fakeList);
    }

    @Test
    public void test_getSortedSetOfCustomersByName() {
        Mockito.when(customerRepository.getCustomersMatchingName(Mockito.anyString())).thenReturn(list);

        List<Customer> customers = customerService.getCustomersMatchingName(EMPTY_STRING);

        assertNotNull(customers);
        assertFalse(customers.isEmpty());
    }

    @Test
    public void test_getCustomersByTopicId() {
        when(customerRepository.getCustomersByTopicId(root.getId())).thenReturn(list);

        List<Customer> tagList = customerService.getCustomersByTopicId(root.getId());

        assertNotNull(tagList);
        assertEquals(tagList, list);
    }

    @Test
    public void test_getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(fakeResult);

        List<Customer> customersList = customerService.getAllCustomers();

        assertNotNull(customersList);
        assertEquals(customersList, list);
    }
}
