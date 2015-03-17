package com.epam.eighty.web.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.service.CustomerService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CustomerControllerTest {

    private static final String TEST_STRING = "fake string";
    private static final long TEST_LONG_ID = 1L;

    @Mock
    private Customer customer;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;

    private List<Customer> customerSet;
    private List<Customer> customerList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Customer customer1 = new Customer();
        customer1.setId(1001L);
        customer1.setName("customer1");
        Customer customer2 = new Customer();
        customer2.setId(1002L);
        customer2.setName("customer2");

        customerSet = new ArrayList<>();
        customerSet.add(customer1);
        customerSet.add(customer2);

        customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
    }

    @Test
    public void test_getAllCustomersByTopicId() {
        when(customerService.getCustomersByTopicId(TEST_LONG_ID)).thenReturn(customerList);

        assertTrue(customerController.getAllCustomersByTopicId(TEST_LONG_ID).equals(customerList));
    }

    @Test
    public void test_getSortedSetOfCustomersByName() {
        when(customerService.getSortedCustomersMatchingName(TEST_STRING)).thenReturn(customerSet);

        assertTrue(customerController.getSortedSetOfCustomersByName(TEST_STRING).equals(customerSet));
    }

    @Test
    public void test_getAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(customerList);

        assertTrue(customerController.getAllCustomers().equals(customerList));
    }

}
