package com.epam.eighty.service;

import com.epam.eighty.domain.Tag;

import java.util.List;
import java.util.Set;

/**
 * Created by Aliaksandr_Padalka on 22/07/2014.
 */

public interface TagService {
    List<Tag> getTagsByTopicId(Long topicId);
    Tag getTagByTag(String title);
    Set<Tag> getAllTags();
    List<Tag> getTopNFromAllTags(Long limit);
    Set<Tag> getSortedSetOfTagsByName(String tagName);

}
