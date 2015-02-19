package com.epam.eighty.web.api;

import com.epam.eighty.domain.Customer;
import com.epam.eighty.service.CustomerService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Set;

/**
 * @author Yauheni_Razhkou
 *
 */
@Api(value = "/customers", description = "All operations for customers")
@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "Find sorted customers", notes = "Get sorted customers by part of customer name", httpMethod = "GET", response = Customer.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json customer"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @RequestMapping(value = "/{customerName}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "customer", key = "#customerName")
    public Set<Customer> getSortedSetOfCustomersByName(@ApiParam(name = "customerName", required = true, value = "sorted set of customers by part of customer name") @PathVariable("customerName") final String customerName) {
        return customerService.getSortedSetOfCustomersByName(customerName);
    }

    @ApiOperation(value = "Find all customers", notes = "Get all customers", httpMethod = "GET", response = Customer.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json customer"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "customer", key = "'all'")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @ApiOperation(value = "Find all customers by topic id", notes = "get all customers by topic id", httpMethod = "GET", response = Customer.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "customer", key = "'topic.' + #id")
    public List<Customer> getAllCustomersByTopicId(@PathVariable("id") final Long id) {
        return customerService.getCustomersByTopicId(id);
    }
}
