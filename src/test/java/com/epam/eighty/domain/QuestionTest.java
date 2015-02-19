package com.epam.eighty.domain;

import static org.junit.Assert.assertTrue;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;

import com.epam.eighty.utils.PropertyAsserter;

/**
 * @author Yauheni_Razhkou
 *
 */
public class QuestionTest {

    private static final int TEST_INT = 1;
    private static final String TEST_STRING = "1";
    private static final long TEST_LONG = 1l;

    @Test
    public void test_basicGetterSetter() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new Question());
    }

    @Test
    public void test_equalsContract() {
        EqualsVerifier.forClass(Question.class)
                .suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void test_toString() {
        Question question = new Question();

        question.setId(TEST_LONG);
        question.setAnswer(TEST_STRING);
        question.setLike(TEST_INT);
        question.setQuestion(TEST_STRING);

        assertTrue(!ObjectUtils.identityToString(question).equals(
                question.toString()));
    }

}
