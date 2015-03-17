package com.epam.eighty.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.stereotype.Repository;

import com.epam.eighty.domain.Customer;

/**
 * @author Yauheni_Razhkou
 *
 */
@Repository("customerRepository")
public interface CustomerRepository extends BaseRepository<Customer, Long> {

    @Query(value = "MATCH (customer:`Customer`) WHERE customer.name =~ ('.*' + {0} + '.*') RETURN customer ORDER BY customer.name", elementClass = Customer.class)
    List<Customer> getCustomersMatchingName(String customerName);

    @Query(value = "MATCH (customer:`Customer`)<-[:`has`*]-(question:`Question`)<-[:`contains`*]-(topic) WHERE ID(topic) = {0} RETURN DISTINCT customer", elementClass = Customer.class)
    List<Customer> getCustomersByTopicId(Long id);

    /**
     * Retrieves a count of questions marked by tag in topic.
     *
     * @param name a customer name
     * @param topicId id of a topic
     * @return count of questions
     */
    @Query(value = "MATCH (customer:`Customer`)<-[r:`has`*]-(question:`Question`)<-[:`contains`*]-(topic:`Topic`) WHERE  customer.name = {0} AND Id(topic) = {1} RETURN count(r)", elementClass = Long.class)
    Long getQuestionsNumberInTopicByCustomer(String name, Long topicId);

}
