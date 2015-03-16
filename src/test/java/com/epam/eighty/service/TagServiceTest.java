package com.epam.eighty.service;

import com.epam.eighty.domain.Tag;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.repository.TagRepository;
import com.epam.eighty.service.impl.TagServiceImpl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.conversion.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by Aliaksandr_Padalka on 23/07/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

    private static final long LIMIT = 5L;
    private static final String ANY_SYMBOL = ".*";

    private Optional<Tag> fake;
    private Result<Tag> results;
    private List<Tag> tags;
    private Topic root;
    private List<Tag> list;

    @Mock
    private TagRepository tagRepo;
    @InjectMocks
    private TagServiceImpl tagService;

    @Before
    public void setUp() {
        fake = Optional.of(new Tag());
        fake.get().setId(10L);
        fake.get().setTag("fake");

        Tag fake1 = new Tag();
        fake1.setId(1001L);
        fake1.setTag("fake1");
        Tag fake2 = new Tag();
        fake2.setId(1002L);
        fake2.setTag("fake2");
        Tag fake3 = new Tag();
        fake3.setId(1003L);
        fake3.setTag("fake3");

        tags = new ArrayList<>();
        tags.add(fake1);
        tags.add(fake2);
        tags.add(fake3);

        root = new Topic();
        root.setId(0L);

        list = new ArrayList<>();
        list.add(fake1);
        list.add(fake2);
        list.add(fake3);

        results = new QueryResultBuilder<>(tags);

    }

    @Test
    public void test_getTagByTag() {
        when(tagRepo.findBySchemaPropertyValue("tag", fake.get().getTag())).thenReturn(fake);
        Tag tag = tagService.getTagByTag(fake.get().getTag());

        assertNotNull(tag);
        assertEquals(tag, fake.get());
    }

    @Test
    @Ignore
    public void test_getTagByTagIfTagIsNull() {
        when(tagRepo.findBySchemaPropertyValue("tag", fake.get().getTag())).thenReturn(Optional.empty());
        Tag tag = tagService.getTagByTag(fake.get().getTag());

        assertNotNull(tag);
        assertNull(tag.getTag());
    }

    @Test
    public void test_findAll() {
        when(tagRepo.findAll()).thenReturn(results);

        List<Tag> set = tagService.getAllTags();

        assertNotNull(set);
        assertEquals(set, tags);
    }

    @Test
    public void test_getTagsByTopicId() {
        when(tagRepo.getTagsByTopicId(root.getId())).thenReturn(list);

        List<Tag> tagList = tagService.getTagsByTopicId(root.getId());

        assertNotNull(tagList);
        assertEquals(tagList, list);
    }

    @Test
    public void test_getTopNFromAllTags() {
        when(tagRepo.getTopNFromAllTags(LIMIT)).thenReturn(list);

        List<Tag> tagList = tagService.getTopNFromAllTags(LIMIT);

        assertNotNull(tagList);
        assertEquals(tagList, list);
    }

    @Test
    public void test_getSortedSetOfTagsByName() {
        when(tagRepo.getSortedListOfTagsByName(ANY_SYMBOL + fake.get().getTag()+ ANY_SYMBOL)).thenReturn(list);

        List<Tag> set = tagService.getSortedSetOfTagsByName(fake.get().getTag());

        assertNotNull(set);
        assertEquals(set, tags);
    }


}
