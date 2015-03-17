/**
 * @ngdoc service
 * @name eightyCrudFactory.crudFactory
 *
 * @description
 * A factory which creates a resource objects that lets you interact with RESTful server-side data sources.
 */
(function () {
    'use strict';
    angular
        .module('eightyFactories')
        .factory('crudFactory', crudFactory);

    crudFactory.$inject = ['$resource'];

    function crudFactory($resource) {

        var factory = {
            topic: topic,
            question: question,
            questions: questions,
            tags: tags,
            customers: customers
        };

        return factory;

        /**
         * @ngdoc method
         * @name topic
         * @methodOf eightyCrudFactory.crudFactory
         *
         * @description
         * Creates a resource object for `topic`.
         *
         * @returns {Object} A resource "class" object with the following set of resource actions:
         *
         * - `{object}` `get({Number} id)` — Request for topic with given id.
         * - `{object}` `update({object} topic)` — Request for update the given topic.
         */
        function topic () {
            return $resource('topics/:id', {}, {
                get: {method: 'GET', params: {id: '@id'}},
                getFull: { method: 'GET', url: 'topics/full/:id', params: {id: '@id'} },
                update: { method: 'PUT', url: 'topics' },
                create: { method: 'POST', params: {id: '@id'}},
                getPath: { method: 'GET', url: 'topics/path/:id', params: {id: '@id'} }
            });
        }

        /**
         * @ngdoc method
         * @name question
         * @methodOf eightyCrudFactory.crudFactory
         *
         * @description
         * Creates a resource object for `question`.
         *
         * @returns {Object} A resource "class" object with the following set of resource actions:
         *
         * - `{object}` `get({Number} id)` — Request for question with given id.
         * - `{object}` `update({object} question)` — Request for update the given question.
         * - `{object}` `create({Number} topicId, {object} question)` — Request for create the question.
         */
        function question() {
            return $resource('questions/:id', {}, {
                get: {method: 'GET', params: {id: '@id'}},
                update: {method: 'PUT', url: 'questions'},
                create: {method: 'POST', params: {id: '@id'}}
            });
        }

        /**
         * @ngdoc method
         * @name questions
         * @methodOf eightyCrudFactory.crudFactory
         *
         * @description
         * Creates a resource object for requesting questions of current topic and it subtopics.
         *
         * @returns {Object} A resource "class" object with the following set of resource actions:
         *
         * - `{object}` `query()` — Request for questions.
         * - `{object}` `allQuestions({Number} id, {Number} page, {Number} size, {String} sort)` — Request for questions of topic with given id.
         * `page` - page number, `size` - number questions per page, `sort` - question field for sorting
         * - `{object}` `allQuestionsInTopicWithTag({Number} id, {String} title)` — Request for questions in selected topic (with id) and questions has tag (with title).
         * - `{object}` `allQuestionsWithTag({String} title)` — Request for questions has tag (with title).
         */
        function questions() {
            return $resource('questions/', {}, {
                query: {method: 'GET', isArray: true},
                allQuestions: {method: 'GET', url: 'questions/all/:id', params: {id: '@id', page: '@page', size: '@size', sort: '@sort'}, isArray: true},
                allQuestionsInTopicWithTag: {method: 'GET', params: {id: '@id', title: '@title'}, url: 'questions/topic/:id/tag/:title', isArray: true},
                allQuestionsWithTag: {method: 'GET', url: 'questions/all/tag/:tagName', params: {tagName: '@tagName'}, isArray: true},
                allQuestionsFromCustomer: {method: 'GET', url: 'questions/all/customer/:customerName', params: {customerName: '@customerName'}, isArray: true}
            });
        }

        /**
         * @ngdoc method
         * @name tags
         * @methodOf eightyCrudFactory.crudFactory
         *
         * @description
         * Get a resource object for requesting tags.
         *
         * @returns {Object} A resource "class" object with the following set of resource actions:
         *
         * - `{object}` `get()` — Request for tags.
         * - `{object}` `getSortedSetOfTagsByName({String} tagName)` — Request for getting sorted tags by part of tag name.
         * - `{object}` `topNTags({Number} limit)` — Request for top tags (due to limit).
         * - `{object}` `tagsByTopicId({Number} id)` — Request for top tags in selected topic.
         */
        function tags() {
            return $resource('tags/', {}, {
                get: {method: 'GET', isArray: true},
                getSortedSetOfTagsByName: {method: 'GET', url: 'tags/:tagName', params: {tagName: '@tagName'}, isArray: true},
                topNTags: {method: 'GET', params: {limit: '@limit'}, url: 'tags/top/:limit', isArray: true},
                tagsByTopicId: {method: 'GET', params: {id: '@id'}, url: 'tags/topic/:id', isArray: true}
            });
        }

        /**
         * @ngdoc method
         * @name customers
         * @methodOf eightyCrudFactory.crudFactory
         *
         * @description
         * Get a resource object for requesting customers.
         *
         * @returns {Object} A resource "class" object with the following set of resource actions:
         *
         * - `{object}` `get()` — Request for customers.
         * - `{object}` `getSortedSetOfCustomersByName({String} customerName)` — Request for getting sorted customers by part of customer name.
         */
        function customers() {
            return $resource('customers/', {}, {
                get: {method: 'GET', isArray: true},
                getSortedSetOfCustomersByName: {method: 'GET', url: 'customers/:customerName', params: {customerName: '@customerName'}, isArray: true},
                getCustomersByTopicId: {method: 'GET', params: {id: '@id'}, url: 'customers/topic/:id', isArray: true}
            });
        }
    }
})();