'use strict';

describe('service', function () {

    beforeEach(module('eightyFactories'));

    it('check the existence of crudFactory', inject(function (crudFactory) {
        expect(crudFactory).toBeDefined();
    }));

    describe('crudFactory', function () {
        var $httpBackend;
        var service;
        var response = {};
        var responseArray = [];
        var fakeResponse = {response: 'fake response'};
        var fakeResponseArray = [
            {response: 'fake response 1'},
            {response: 'fake response 2'}
        ];

        beforeEach(inject(function (_$httpBackend_, crudFactory) {
            $httpBackend = _$httpBackend_;
            service = crudFactory;
            response = {};
            responseArray = [];
        }));

        function expectResponse() {
            $httpBackend.flush();
            expect(response.response).toBe(fakeResponse.response);
        }

        function expectResponseArray() {
            $httpBackend.flush();
            expect(responseArray[0].response).toBe(fakeResponseArray[0].response);
            expect(responseArray[1].response).toBe(fakeResponseArray[1].response);
        }

        it('get topic by id', function () {
            $httpBackend.expectGET('topics/1').respond(fakeResponse);
            service.topic().get({id: '1'}).$promise.then(function (responseTopic) {
                response = responseTopic;
            });
            expectResponse();
        });

        it('get id of the last not removed topic', function () {
            $httpBackend.expectPOST('topics/notRemoved').respond(fakeResponse);
            service.topic().getLastNotRemoved([1, 2]).$promise.then(function (responseTopic) {
                response = responseTopic;
            });
            expectResponse();
        });


        it('update topic with id', function () {
            $httpBackend.expectPUT('topics').respond(fakeResponse);
            service.topic().update(response).$promise.then(function (responseTopic) {
                response = responseTopic;
            });
            expectResponse();
        });

        it('create topic', function () {
            $httpBackend.expectPOST('topics/15', fakeResponse).respond(200);
            service.topic().create({id: '15'}, fakeResponse).$promise.then(function (responseTopic) {
                response = responseTopic;
            });
            expectResponse();
        });
 
        it('get path', function() {
            $httpBackend.expectGET('topics/path/5').respond(fakeResponse);
            service.topic().getPath({id: '5'}).$promise.then(function (responseTopic) {
                response = responseTopic;
            });
            expectResponse();
        });

        it('get question by id', function () {
            $httpBackend.expectGET('questions/1').respond(fakeResponse);
            service.question().get({id: '1'}).$promise.then(function (responseQuestion) {
                response = responseQuestion;
            });
            expectResponse();
        });

        it('update question with id', function () {
            $httpBackend.expectPUT('questions').respond(fakeResponse);
            service.question().update(response).$promise.then(function (responseQuestion) {
                response = responseQuestion;
            });
            expectResponse();
        });

        it('create question', function () {
            $httpBackend.expectPOST('questions/15', fakeResponse).respond(200);
            service.question().create({id: '15'}, fakeResponse).$promise.then(function (responseQuestion) {
                response = responseQuestion;
            });
            expectResponse();
        });

        it('get questions', function () {
            $httpBackend.expectGET('questions').respond(fakeResponseArray);
            service.questions().query().$promise.then(function (responseQuestions) {
                responseArray = responseQuestions;
            });
            expectResponseArray();
        });

        it('get all questions', function () {
            $httpBackend.expectGET('questions/all/1').respond(fakeResponseArray);
            service.questions().allQuestions({id: '1'}).$promise.then(function (responseQuestions) {
                responseArray = responseQuestions;
            });
            expectResponseArray();
        });

        it('get all questions in topic with tag', function () {
            $httpBackend.expectGET('questions/topic/1/tag/fakeTag').respond(fakeResponseArray);
            service.questions().allQuestionsInTopicWithTag({id: '1', title: 'fakeTag'}).$promise.then(function (responseQuestions) {
                responseArray = responseQuestions;
            });
            expectResponseArray();
        });

        it('get all questions with tag', function () {
            $httpBackend.expectGET('questions/all/tag/fakeTag').respond(fakeResponseArray);
            service.questions().allQuestionsWithTag({tagName: 'fakeTag'}).$promise.then(function (responseQuestions) {
                responseArray = responseQuestions;
            });
            expectResponseArray();
        });

        it('get all questions from customer', function () {
            $httpBackend.expectGET('questions/all/customer/fakeCustomer').respond(fakeResponseArray);
            service.questions().allQuestionsFromCustomer({customerName: 'fakeCustomer'}).$promise.then(function (responseQuestions) {
                responseArray = responseQuestions;
            });
            expectResponseArray();
        });

        it('get all tags', function () {
            $httpBackend.expectGET('tags').respond(fakeResponseArray);
            service.tags().get().$promise.then(function (responseTags) {
                responseArray = responseTags;
            });
            expectResponseArray();
        });

        it('get sorted set of tags by name', function () {
            $httpBackend.expectGET('tags/tag1').respond(fakeResponseArray);
            service.tags().getSortedSetOfTagsByName({tagName: 'tag1'}).$promise.then(function (responseTags) {
                responseArray = responseTags;
            });
            expectResponseArray();
        });

        it('get top N tags', function () {
            $httpBackend.expectGET('tags/top/2').respond(fakeResponseArray);
            service.tags().topNTags({limit: '2'}).$promise.then(function (responseTags) {
                responseArray = responseTags;
            });
            expectResponseArray();
        });

        it('get tags by topic id', function () {
            $httpBackend.expectGET('tags/topic/1').respond(fakeResponseArray);
            service.tags().tagsByTopicId({id: '1'}).$promise.then(function (responseTags) {
                responseArray = responseTags;
            });
            expectResponseArray();
        });

        it('get all customers', function () {
            $httpBackend.expectGET('customers').respond(fakeResponseArray);
            service.customers().get().$promise.then(function (responseCustomers) {
                responseArray = responseCustomers;
            });
            expectResponseArray();
        });

        it('get sorted set of customers by name', function () {
            $httpBackend.expectGET('customers/customer1').respond(fakeResponseArray);
            service.customers().getSortedSetOfCustomersByName({customerName: 'customer1'}).$promise.then(function (responseCustomers) {
                responseArray = responseCustomers;
            });
            expectResponseArray();
        });

        it('get customers by topic id', function () {
            $httpBackend.expectGET('customers/topic/1').respond(fakeResponseArray);
            service.customers().getCustomersByTopicId({id: '1'}).$promise.then(function (responseCustomers) {
                responseArray = responseCustomers;
            });
            expectResponseArray();
        });
    });
});