package com.epam.eighty.service;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.conversion.Result;

import com.epam.eighty.domain.Tag;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.repository.TagRepository;
import com.epam.eighty.service.impl.TagServiceImpl;

/**
 * Created by Aliaksandr_Padalka on 23/07/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

    private static final long QUESTIONS_NUMBER = 9999L;
    private static final long LIMIT = 5L;
    private static final String TAG_NAME = "tag name";

    private Result<Tag> results;
    private List<Tag> tags;
    private Topic root;

    @Mock
    private TagRepository tagRepo;
    @InjectMocks
    private TagServiceImpl tagService;

    @Before
    public void setUp() {
        Tag tag1 = new Tag();
        tag1.setId(1001L);
        tag1.setTag("fake1");
        Tag tag2 = new Tag();
        tag2.setId(1002L);
        tag2.setTag("fake2");
        Tag tag3 = new Tag();
        tag3.setId(1003L);
        tag3.setTag("fake3");

        tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        root = new Topic();
        root.setId(0L);

        results = new QueryResultBuilder<>(tags);
    }

    @Test
    public void test_getAllTags() {
        when(tagRepo.findAll()).thenReturn(results);

        List<Tag> actualTags = tagService.getAllTags();

        assertThat(actualTags, contains(tags.toArray()));
    }

    @Test
    public void test_getTagsByTopicId() {
        when(tagRepo.getTagsByTopicId(root.getId())).thenReturn(tags);
        when(tagRepo.getQuestionsNumberInTopicByTag(Mockito.anyString(), Mockito.anyLong())).thenReturn(QUESTIONS_NUMBER);

        List<Tag> actualTags = tagService.getTagsByTopicId(root.getId());

        actualTags.forEach(tag -> assertEquals(QUESTIONS_NUMBER, tag.getCountInTopic().longValue()));
        assertThat(actualTags, contains(tags.toArray()));
    }

    @Test
    public void test_getTopNFromAllTags() {
        when(tagRepo.getTopNFromAllTags(LIMIT)).thenReturn(tags);

        List<Tag> actualTags = tagService.getTopNFromAllTags(LIMIT);

        assertThat(actualTags, contains(tags.toArray()));
    }

    @Test
    public void test_getTagsMatchingName() {
        when(tagRepo.getTagsMatchingName(TAG_NAME)).thenReturn(tags);

        List<Tag> actualTags = tagService.getTagsMatchingName(TAG_NAME);

        assertThat(actualTags, contains(tags.toArray()));
    }

}
