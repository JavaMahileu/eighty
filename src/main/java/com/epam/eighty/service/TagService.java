package com.epam.eighty.service;

import com.epam.eighty.domain.Tag;

import java.util.List;

/**
 * Created by Aliaksandr_Padalka on 22/07/2014.
 */

public interface TagService {
    List<Tag> getTagsByTopicId(Long topicId);
    List<Tag> getAllTags();
    List<Tag> getTopNFromAllTags(Long limit);
    List<Tag> getSortedTagsMatchingName(String tagName);
}
