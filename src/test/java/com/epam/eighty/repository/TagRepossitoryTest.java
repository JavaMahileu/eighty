package com.epam.eighty.repository;

import com.epam.eighty.domain.Question;
import com.epam.eighty.domain.Tag;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.resources.TestNeo4jConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Aliaksandr_Padalka on 23/07/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestNeo4jConfig.class)
@Transactional
public class TagRepossitoryTest {

    private static final Long LIMIT = 1L;

    @Autowired
    private TagRepository tagRepo;
    @Autowired
    private TopicRepository topicRepo;
    @Autowired
    private QuestionRepository questionRepo;

    @Test
    public void test_AddTag() {
        Tag faketag = new Tag();
        faketag.setTag("fake tag");

        Tag tag = tagRepo.save(faketag);

        assertNotNull(tag);
        assertEquals(tag, faketag);

        tagRepo.delete(tag);
    }

    @Test
    public void test_FindByTag() {
        Tag faketag = new Tag();
        faketag.setTag("fake tag");

        faketag = tagRepo.save(faketag);

        Optional<Tag> tag = tagRepo.findBySchemaPropertyValue("tag", faketag.getTag());

        assertTrue(tag.isPresent());
        assertEquals(tag.get(), faketag);

        tagRepo.delete(tag.get());
    }

    @Test
    public void test_getTagsByTopicId() {
        Topic root = new Topic();
        root.setTitle("root");

        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");
        Question q3 = new Question();
        q3.setQuestion("q3");

        Tag t1 = new Tag();
        t1.setTag("t1");
        Tag t2 = new Tag();
        t2.setTag("t2");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(t1);
        Set<Tag> tags2 = new HashSet<>();
        tags2.add(t2);

        q1.setTags(tags1);
        q2.setTags(tags1);
        q3.setTags(tags2);

        Set<Question> questions = new HashSet<>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        root.setQuestions(questions);
        topicRepo.save(root);

        Slice<Tag> tags = tagRepo.getTagsByTopicId(root.getId());
        assertNotNull(tags);
        assertEquals(tags.getContent().size(), 2);

        tagRepo.delete(t1);
        tagRepo.delete(t2);
        questionRepo.delete(q1);
        questionRepo.delete(q2);
        questionRepo.delete(q3);
        topicRepo.delete(root);
    }

    @Test
    public void test_getTopNFromAllTags() {
        Topic root = new Topic();
        root.setTitle("root");

        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");
        Question q3 = new Question();
        q3.setQuestion("q3");

        Tag t1 = new Tag();
        t1.setTag("t1");
        Tag t2 = new Tag();
        t2.setTag("t2");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(t1);
        Set<Tag> tags2 = new HashSet<>();
        tags2.add(t2);

        q1.setTags(tags1);
        q2.setTags(tags1);
        q3.setTags(tags2);

        Set<Question> questions = new HashSet<Question>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        root.setQuestions(questions);
        topicRepo.save(root);

        Slice<Tag> tags = tagRepo.getTopNFromAllTags(LIMIT);
        assertNotNull(tags);
        assertEquals(tags.getContent().size(), 1);

        tagRepo.delete(t1);
        tagRepo.delete(t2);
        questionRepo.delete(q1);
        questionRepo.delete(q2);
        questionRepo.delete(q3);
        topicRepo.delete(root);
    }

    @Test
    public void test_RemoveTagsWithoutQuestions() {
        Tag faketag = new Tag();
        faketag.setTag("fake tag");

        Tag tag = tagRepo.save(faketag);
        assertNotNull(tag);
        tagRepo.removeTagsWithoutQuestion();

        assertFalse(tagRepo.findBySchemaPropertyValue("tag", "fake tag").isPresent());
    }

    @Test
    public void test_getSortedSetOfTagsByName() {
        Tag t1 = new Tag();
        t1.setTag("fake tag 1");
        tagRepo.save(t1);
        Tag t2 = new Tag();
        t2.setTag("fake tag 2");
        tagRepo.save(t2);

        Slice<Tag> tags = tagRepo.getSortedSetOfTagsByName("fake tag.*");
        assertNotNull(tags);
        assertEquals(tags.getContent().size(), 2);
        tagRepo.delete(t1);
        tagRepo.delete(t2);
    }

    @Test
    public void test_getQuestionsInTopicByTag() {
        Topic root = new Topic();
        root.setTitle("root");

        Question q1 = new Question();
        q1.setQuestion("q1");
        Question q2 = new Question();
        q2.setQuestion("q2");

        Tag tag1 = new Tag();
        tag1.setTag("t1");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);

        q1.setTags(tags1);
        q2.setTags(tags1);

        Set<Question> questions = new HashSet<>();
        questions.add(q1);
        questions.add(q2);

        root.setQuestions(questions);
        topicRepo.save(root);

        long questionCount = tagRepo.getQuestionsInTopicByTag(tag1.getTag(), root.getId());
        assertEquals(questionCount, 2);

        tagRepo.delete(tag1);
        questionRepo.delete(q1);
        questionRepo.delete(q2);
        topicRepo.delete(root);
    }

}
