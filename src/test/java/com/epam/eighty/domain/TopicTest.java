package com.epam.eighty.domain;

import static org.junit.Assert.assertTrue;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.epam.eighty.utils.PropertyAsserter;

public class TopicTest {

    private static final String TEST_STRING = "1";
    private static final long TEST_LONG = 1l;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_basicGetterSetter() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new Topic());
    }

    @Test
    public void test_equalsContract() {
        EqualsVerifier.forClass(Topic.class).suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void test_toString() {

        Topic topic = new Topic();

        topic.setId(TEST_LONG);
        topic.setTitle(TEST_STRING);
        topic.setQuestions(null);
        topic.setTopics(null);

        assertTrue(!ObjectUtils.identityToString(topic)
                .equals(topic.toString()));
    }

}
