package com.epam.eighty.web.api;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.epam.eighty.domain.Tag;
import com.epam.eighty.service.TagService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliaksandr_Padalka on 23/07/2014.
 */
public class TagControllerTest {

    private static final String TEST_STRING = "fake string";
    private static final long TEST_LONG_ID = 1L;
    private static final long TEST_LONG_LIMIT = 5L;

    @Mock
    private Tag tag;
    @Mock
    private TagService tagService;
    @InjectMocks
    private TagController tagController;

    private List<Tag> tagSet;
    private List<Tag> tagList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Tag fake1 = new Tag();
        fake1.setId(1001L);
        fake1.setTag("fake1");
        Tag fake2 = new Tag();
        fake2.setId(1002L);
        fake2.setTag("fake2");
        Tag fake3 = new Tag();
        fake3.setId(1003L);
        fake3.setTag("fake3");

        tagSet = new ArrayList<>();
        tagSet.add(fake1);
        tagSet.add(fake2);
        tagSet.add(fake3);

        tagList = new ArrayList<>();
        tagList.add(fake1);
        tagList.add(fake2);
        tagList.add(fake3);
    }

    @Test
    public void test_getTagByTag() {
        when(tagService.getTagByTag(TEST_STRING)).thenReturn(tag);
        assertTrue(tagController.getTagByTag(TEST_STRING).equals(tag));
    }

    @Test
    public void test_getAllTagsByTopicId() {
        when(tagService.getTagsByTopicId(TEST_LONG_ID)).thenReturn(tagList);
        assertTrue(tagController.getAllTagsByTopicId(TEST_LONG_ID).equals(tagList));
    }

    @Test
    public void test_getSortedSetOfTagsByName() {
        when(tagService.getSortedTagsMatchingName(TEST_STRING)).thenReturn(tagSet);
        assertTrue(tagController.getSortedSetOfTagsByName(TEST_STRING).equals(tagSet));
    }

    @Test
    public void test_getAllTags() {
        when(tagService.getAllTags()).thenReturn(tagSet);
        assertTrue(tagController.getAllTags().equals(tagSet));
    }

    @Test
    public void test_getTopNFromAllTags() {
        when(tagService.getTopNFromAllTags(TEST_LONG_LIMIT)).thenReturn(tagList);
        assertTrue(tagController.getTopNFromAllTags(TEST_LONG_LIMIT).equals(tagList));
    }
}
