package com.epam.eighty.web.api;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.epam.eighty.domain.Topic;
import com.epam.eighty.exception.TopicNotFoundException;
import com.epam.eighty.service.DBPopulatorService;
import com.epam.eighty.service.TopicService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @author Aliaksandr_Padalka
 */
@Api(value = "/topics", description = "All operations for topics")
@Controller
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private DBPopulatorService dbService;

    @ApiOperation(value = "Find topic by id without questions", notes = "Get topic by id without questions", httpMethod = "GET", response = Topic.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json topic"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found") })
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "topic", key = "#id")
    public Topic getTopic(@ApiParam(name = "topicId", required = true, value = "topic id") @PathVariable("id") final Long id) {
        return topicService.getTopicById(id).orElseThrow(() -> new TopicNotFoundException(id));
    }

    @ApiOperation(value = "Find topic by id with questions", notes = "Get topic by id with questions", httpMethod = "GET", response = Topic.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json topic"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found") })
    @Timed
    @RequestMapping(value = "/full/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "topic", key = "'full.' + #id")
    public Topic getFullTopic(@ApiParam(name = "topicId", required = true, value = "topic id") @PathVariable("id") final Long id) {
        return topicService.getFullTopicById(id).orElseThrow(() -> new TopicNotFoundException(id));
    }

    @ApiOperation(value = "Find root topic", notes = "Get root topic", httpMethod = "GET", response = Topic.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json topic with title 'root'"),
            @ApiResponse(code = 404, message = "Not found") })
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "topic", key = "'root.' + #id")
    public Topic getRootTopic() throws IOException {
        Optional <Topic> optionalTopic = topicService.getRoot();
        if (!optionalTopic.isPresent()) {
            dbService.populate();
            optionalTopic = topicService.getRoot();
        }
        return optionalTopic.orElseThrow(TopicNotFoundException::new);
    }

    @ApiOperation(value = "Delete topic by id", notes = "Delete topic by id", httpMethod = "DELETE")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Invalid ID"), })
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Caching(evict = {
            @CacheEvict(value = "topic", allEntries = true),
            @CacheEvict(value = "question", allEntries = true),
            @CacheEvict(value = "tag", allEntries = true),
            @CacheEvict(value = "customer", allEntries = true)
        })
    public void deleteTopic(@ApiParam(name = "topicId", required = true, value = "topic id") @PathVariable("id") final Long id, final HttpServletResponse response) {
        topicService.deleteTopic(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @ApiOperation(value = "Create topic", notes = "Create topic", httpMethod = "POST", response = Topic.class, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @CacheEvict(value = "topic", allEntries = true)
    public Topic createTopic(@ApiParam(name = "topic", required = true, value = "topic") @RequestBody final Topic topic,
            @ApiParam(name = "id", required = true, value = "id") @PathVariable("id") final Long id, final HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return topicService.createTopic(topic, id);
    }

    @ApiOperation(value = "Update topic", notes = "Update topic", httpMethod = "PUT", response = Topic.class, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @CacheEvict(value = "topic", allEntries = true)
    public Topic updateTopic(@ApiParam(name = "topic", required = true, value = "topic") @RequestBody final Topic topic, final HttpServletResponse response) {
        topicService.updateTopic(topic);
        response.setStatus(HttpServletResponse.SC_OK);
        return topic;
    }

}
