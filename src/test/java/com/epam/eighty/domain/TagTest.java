package com.epam.eighty.domain;

import static org.junit.Assert.assertEquals;
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
public class TagTest {

    private static final String TEST_STRING = "1";
    private static final long TEST_LONG = 1l;

    @Test
    public void test_basicGetterSetter() {
        PropertyAsserter.assertBasicGetterSetterBehavior(new Tag());
    }

    @Test
    public void test_equalsContract() {
        EqualsVerifier.forClass(Tag.class).suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void test_toString() {
        Tag tag = new Tag();

        tag.setId(TEST_LONG);
        tag.setTag(TEST_STRING);

        assertTrue(!ObjectUtils.identityToString(tag).equals(tag.toString()));
    }

    @Test
    public void test_compareTo() {
        Tag tag1 = new Tag();
        tag1.setId(TEST_LONG);
        tag1.setTag(TEST_STRING);
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setTag(TEST_STRING);

        assertEquals(0, tag1.compareTo(tag2));
    }

    @Test
    public void test_compareToIfNull() {
        Tag tag1 = new Tag();
        tag1.setId(TEST_LONG);
        tag1.setTag(TEST_STRING);
        Tag tag2 = null;

        assertEquals(1, tag1.compareTo(tag2));
    }
}
