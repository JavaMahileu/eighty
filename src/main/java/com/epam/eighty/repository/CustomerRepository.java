package com.epam.eighty.repository;

import com.epam.eighty.domain.Customer;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yauheni_Razhkou
 *
 */
@Repository("customerRepository")
public interface CustomerRepository extends GraphRepository<Customer> {

    @Query(value = "MATCH (customer:`Customer`) WHERE customer.name =~{0} RETURN customer", elementClass = Customer.class)
    Slice<Customer> getSortedSetOfCustomersByName(String customerName);

    @Query(value = "MATCH (customer:`Customer`)<-[:`has`*]-(question:`Question`)<-[:`contains`*]-(topic) WHERE ID(topic) = {0} RETURN DISTINCT customer", elementClass = Customer.class)
    Slice<Customer> getCustomersByTopicId(Long id);

    /**
     * Retrieves a count of questions marked by tag in topic.
     *
     * @param name a customer name
     * @param topicId id of a topic
     * @return count of questions
     */
    @Query(value = "MATCH (customer:`Customer`)<-[r:`has`*]-(question:`Question`)<-[:`contains`*]-(topic:`Topic`) WHERE  customer.name = {0} AND Id(topic) = {1} RETURN count(r)", elementClass = Long.class)
    Long getQuestionsInTopicByCustomer(String name, Long topicId);
}
