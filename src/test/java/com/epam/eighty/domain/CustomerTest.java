package com.epam.eighty.domain;

import com.epam.eighty.utils.PropertyAsserter;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Aliaksandr_Padalka on 29/07/2014.
 */
public class CustomerTest {

    private static final long TEST_LONG = 1l;
    private static final String TEST_STRING = "1";

    @Test
    public void test_basicGetterSetter() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new Customer());
    }

    @Test
    public void test_equalsContract() {
        EqualsVerifier.forClass(Customer.class).suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void test_toString() {
        Customer customer = new Customer();

        customer.setId(TEST_LONG);
        customer.setName(TEST_STRING);

        assertTrue(!ObjectUtils.identityToString(customer).equals(customer.toString()));
    }

    @Test
    public void test_compareTo() {
        Customer customer1 = new Customer();
        customer1.setId(TEST_LONG);
        customer1.setName(TEST_STRING);
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName(TEST_STRING);

        assertEquals(0, customer1.compareTo(customer2));
    }

    @Test
    public void test_compareToIfNull() {
        Customer customer1 = new Customer();
        customer1.setId(TEST_LONG);
        customer1.setName(TEST_STRING);
        Customer customer2 = null;

        assertEquals(1, customer1.compareTo(customer2));
    }
}
