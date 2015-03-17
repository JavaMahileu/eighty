package com.epam.eighty.service.impl;

import com.epam.eighty.domain.Tag;
import com.epam.eighty.repository.TagRepository;
import com.epam.eighty.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Aliaksandr_Padalka on 22/07/2014.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepo;

    @Override
    public List<Tag> getTagsByTopicId(final Long topicId) {
        final List<Tag> tagsList  = tagRepo.getTagsByTopicId(topicId);
        tagsList
            .forEach(tag -> tag.setCountInTopic(tagRepo.getQuestionsNumberInTopicByTag(tag.getTag(), topicId)));
        return tagsList;
    }

    @Override
    public Tag getTagByTag(final String title) {
        return tagRepo.findBySchemaPropertyValue("tag", title).orElse(tagRepo.save(new Tag(title))); //TODO should we create new tag if it's absent?
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAll().as(List.class);
    }

    @Override
    public List<Tag> getTopNFromAllTags(final Long limit) {
        return tagRepo.getTopNFromAllTags(limit);
    }

    @Override
    public List<Tag> getSortedTagsMatchingName(final String tagName) {
        return tagRepo.getSortedTagsMatchingName(tagName);
    }

}
