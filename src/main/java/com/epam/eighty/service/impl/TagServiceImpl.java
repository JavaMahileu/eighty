package com.epam.eighty.service.impl;

import com.epam.eighty.domain.Tag;
import com.epam.eighty.repository.TagRepository;
import com.epam.eighty.service.TagService;
import com.epam.eighty.utility.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Aliaksandr_Padalka on 22/07/2014.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private static final String ANY_SYMBOL = ".*";

    @Autowired
    private TagRepository tagRepo;

    @Override
    public List<Tag> getTagsByTopicId(final Long topicId) {
        List<Tag> tagsList  = tagRepo.getTagsByTopicId(topicId).getContent();
        tagsList
            .forEach(tag -> tag.setCountInTopic(tagRepo.getQuestionsInTopicByTag(tag.getTag(), topicId)));
        return tagsList;
    }

    @Override
    public Tag getTagByTag(final String title) {
        Tag tag = tagRepo.findBySchemaPropertyValue("tag", title);
        if (tag == null) {
            tag = new Tag();
            tagRepo.save(tag);
        }
        return tag;
    }

    @Override
    public Set<Tag> getAllTags() {
        return Converter.convertToHashSet(tagRepo.findAll());
    }

    @Override
    public List<Tag> getTopNFromAllTags(final Long limit) {
        return tagRepo.getTopNFromAllTags(limit).getContent();
    }

    @Override
    public Set<Tag> getSortedSetOfTagsByName(final String tagName) {
        return Converter.convertToTreeSet(tagRepo
            .getSortedSetOfTagsByName(ANY_SYMBOL + tagName + ANY_SYMBOL));
    }

}
