package com.epam.eighty.utility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aliaksandr_Padalka on 08/09/2014.
 */
public class ConverterTest {

    @Test
    public void test_getInstance() {
        Converter converter1 = Converter.getInstance();
        Converter converter2 = Converter.getInstance();
        assertEquals(converter1, converter2);
    }
}
