package com.epam.eighty.web.api;

import com.codahale.metrics.annotation.Timed;
import com.epam.eighty.domain.Tag;
import com.epam.eighty.service.TagService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import scala.tools.nsc.typechecker.Tags;

import java.util.List;
import java.util.Set;

/**
 * Created by Aliaksandr_Padalka on 22/07/2014.
 */
@Api(value = "/tags", description = "All operations for tags")
@Controller
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "Find all tags by topic id", notes = "get all tags by topic id", httpMethod = "GET", response = Tag.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "tag", key = "'topic.' + #id")
    public List<Tag> getAllTagsByTopicId(@ApiParam(name = "topicId", required = true, value = "topic id") @PathVariable("id") final Long id) {
        return tagService.getTagsByTopicId(id);
    }

    @ApiOperation(value = "Find tag by tag if exist or create and return tag if not exist", notes = "Get tag by tag if exist or create and return tag if not exist", httpMethod = "GET", response = Tags.class, produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "application/json question") })
    @Timed
    @RequestMapping(value = "/tag/{title}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "tag", key = "'tag.' + #title")
    public Tag getTagByTag(@ApiParam(name = "tag title", required = true, value = "tag") @PathVariable("title") final String title) {
        return tagService.getTagByTag(title);
    }

    @ApiOperation(value = "Find all tags", notes = "Get all tags", httpMethod = "GET", response = Tag.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json tag"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "tag", key = "'all'")
    public Set<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @ApiOperation(value = "Find sorted tags", notes = "Get sorted tags by part of tag name", httpMethod = "GET", response = Tag.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json tag"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(value = "/{tagName}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "tag", key = "'topic.' + #tagName")
    public Set<Tag> getSortedSetOfTagsByName(@ApiParam(name = "tag", required = true, value = "sorted set of tags by part of tag name") @PathVariable("tagName") final String tagName) {
        return tagService.getSortedSetOfTagsByName(tagName);
    }

    @ApiOperation(value = "Find top tags", notes = "get top tags", httpMethod = "GET", response = Tag.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(value = "/top/{limit}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "tag", key = "'top'")
    public List<Tag> getTopNFromAllTags(@ApiParam(name = "limit", required = true, value = "top count") @PathVariable("limit") final Long limit) {
        return tagService.getTopNFromAllTags(limit);
    }
}
