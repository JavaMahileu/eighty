package com.epam.eighty.web.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.epam.eighty.domain.Question;
import com.epam.eighty.service.QuestionService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @author Aliaksandr_Padalka
 */
@Api(value = "/questions", description = "All operations for questions")
@Controller
@RequestMapping("/questions")
public class QuestionController {

    private static final int DEFAULT_PAGE_SIZE = 15;

    private static final String DEFAULT_SORTING = "a.question";

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "Update question", notes = "Update question", httpMethod = "PUT", response = Question.class, consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @Caching(evict = {
            @CacheEvict(value = "question", allEntries = true),
            @CacheEvict(value = "tag", allEntries = true),
            @CacheEvict(value = "customer", allEntries = true)
        })
    public Question updateQuestion(@ApiParam(name = "question", required = true, value = "question") @RequestBody final Question question, final HttpServletResponse response) {
        questionService.updateQuestion(question);
        response.setStatus(HttpServletResponse.SC_OK);
        return question;
    }

    @ApiOperation(value = "Find question by id", notes = "Get question by id", httpMethod = "GET", response = Question.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found") })
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "question", key = "#id")
    public Question getQuestion(@ApiParam(name = "questionId", required = true, value = "question id") @PathVariable("id") final Long id) {
        return questionService.getQuestionById(id);
    }

    @ApiOperation(value = "Create new question", notes = "Create new question", httpMethod = "POST", response = Question.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "application/json question"),
            @ApiResponse(code = 400, message = "Bad request"), })
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Caching(evict = {
            @CacheEvict(value = "question", allEntries = true),
            @CacheEvict(value = "tag", allEntries = true),
            @CacheEvict(value = "customer", allEntries = true)
        })
    public Question createQuestion(@ApiParam(name = "question", required = true, value = "question") @RequestBody final Question question,
            @ApiParam(name = "topicId", required = true, value = "topic id") @PathVariable("id") final Long id, final HttpServletResponse response) {
        questionService.addQuestion(question, id);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return question;
    }

    @ApiOperation(value = "Delete question by id", notes = "Delete question by id", httpMethod = "DELETE")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Invalid ID"), })
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Caching(evict = {
            @CacheEvict(value = "question", allEntries = true),
            @CacheEvict(value = "tag", allEntries = true),
            @CacheEvict(value = "customer", allEntries = true)
        })
    public void deleteQuestion(@ApiParam(name = "questionId", required = true, value = "question id") @PathVariable("id") final Long id, final HttpServletResponse response) {
        questionService.deleteQuestion(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @ApiOperation(value = "Find questions for topic and subtopics", notes = "Get all questions from topic and subtopics", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "application/json question") })
    @Timed
    @RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "question", key = "'all.' + #id")
    public List<Question> getAllQuestionsForTopic(@ApiParam(name = "topicId", required = true, value = "topic id") @PathVariable final Long id,
            @ApiParam(name = "page", required = false, value = "page (@PageableDefault, Pageable)")
                @PageableDefault(page = 0, size = DEFAULT_PAGE_SIZE, sort = DEFAULT_SORTING) final Pageable p) {
        return questionService.getQuestionsByTopicId(id, p);
    }

    @ApiOperation(value = "Find questions for topic and tag", notes = "Get all questions from topic and tag", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "application/json question") })
    @Timed
    @RequestMapping(value = "/topic/{id}/tag/{tag}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "question", key = "'topic.' + #id +'.tag.' + #tag")
    public List<Question> getAllQuestionsForTopicWithTag(@ApiParam(name = "topicId", required = true, value = "topic id") @PathVariable final Long id,
                                                         @ApiParam(name = "tag", required = true, value = "tag's value") @PathVariable final String tag) {
        return questionService.getQuestionsByTopicIdAndTag(id, findDOT(tag));
    }

    @ApiOperation(value = "Find all questions", notes = "Get all questions", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "application/json question") })
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "question", key = "'all'")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @ApiOperation(value = "Find questions with tag", notes = "Get all questions with tag", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "application/json question") })
    @Timed
    @RequestMapping(value = "/all/tag/{tag}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "question", key = "'all.tag.' + #tag")
    public List<Question> getAllQuestionsByTag(@ApiParam(name = "tag", required = true, value = "tag's value") @PathVariable final String tag) {
        return questionService.getQuestionsByTag(findDOT(tag));
    }

    @ApiOperation(value = "Find questions from customer", notes = "Get all questions from customer", httpMethod = "GET", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "application/json question") })
    @Timed
    @RequestMapping(value = "/all/customer/{customer}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(value = "question", key = "'all.customer.' + #customer")
    public List<Question> getAllQuestionsByCustomer(@ApiParam(name = "customer", required = true, value = "customer's value") @PathVariable final String customer) {
        return questionService.getQuestionsByCustomerName(findDOT(customer));
    }

    private String findDOT(final String string) {
        String query;
        if (string.contains(String.valueOf('|'))) {
            query = string.replace('|', '.');
        } else {
            query = string;
        }
        return query;
    }
}
