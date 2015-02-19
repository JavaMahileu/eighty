package com.epam.eighty.web;

import static org.junit.Assert.assertEquals;

import com.wordnik.swagger.model.ApiInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SwaggerControllerTest {

    @InjectMocks
    private SwaggerController swaggerController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_swaggerControllerConstructor() {
        assertEquals(swaggerController.getBaseControllerPackage(), "com.epam.eighty.web.api");
        assertEquals(swaggerController.getBaseModelPackage(), "com.epam.eighty.domain");
        ApiInfo apiInfo = new ApiInfo("eighty app", "", "", "", "", "");
        assertEquals(swaggerController.getApiInfo(), apiInfo);
    }

    @Test
    public void test_documentation() {    
        assertEquals(swaggerController.documentation(), "/docu");
    }
}
