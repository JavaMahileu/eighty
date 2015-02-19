/**
 * Created by Aliaksandr_Padalka on 01/09/2014.
 */
'use strict';

describe('filters', function() {
    var tag1 = {id:11, tag:'test'};
    var tag2 = {id:12, tag:'text'};
    var tag3 = {id:13, tag:'testing'};

    var cust1 = {id:31, name:'cust 1'};
    var cust2 = {id:32, name:'cust 2'};
    var cust3 = {id:33, name:'cust 3'};

    var q1 = {id:1, question:'q test 1', answer:'a test 1', tags:[tag1, tag3], customers:[cust1, cust2], like:0};
    var q2 = {id:2, question:'q text 2', answer:'a test 2', tags:[tag2, tag3], customers:[cust2, cust3], like:0};
    var q3 = {id:3, question:'q test 3', answer:'a text 3', tags:[tag1, tag2], customers:[cust1, cust3], like:0};
    var q4 = {id:4, question:'q text 4', answer:'a text 4', tags:[tag1, tag2, tag3], customers:[cust1, cust3], like:0};
    var q5 = {id:5, question:'q test 5', answer:'a test 5', tags:[tag1], customers:[cust1, cust3], like:0};

    var setQuestions = [q1, q2, q3, q4, q5];
    var searchText = 'text';
    var resultBySearchText = [q2, q3, q4];
    var tags = [tag2, cust3];
    var resultByTagsOrCustomers = [q2, q3, q4, q5];

    var questionFilter, tagFilter;

    beforeEach(module('eightyFilters'));

    beforeEach(inject(function($filter) {
        questionFilter = $filter('questionFilter');
        tagFilter = $filter('tagFilter');
    }));

    it('has a been defined filters', function() {
        expect(questionFilter).toBeDefined();
        expect(tagFilter).toBeDefined();
    });

    it('questionFilter', function() {
        expect(questionFilter(setQuestions, '')).toEqual(setQuestions);
        expect(questionFilter(setQuestions, searchText)).toEqual(resultBySearchText);
    });

    it('tagFilter', function() {
        expect(tagFilter(setQuestions, [])).toEqual(setQuestions);
        expect(tagFilter(setQuestions, tags)).toEqual(resultByTagsOrCustomers);
    });

});
