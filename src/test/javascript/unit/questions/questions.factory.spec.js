'use strict';

describe('service', function () {

    beforeEach(module('eightyApp'));

    it('check the existence of modalData', inject(function (modalData) {
        expect(modalData).toBeDefined();
    }));

    it('check the existence of questionsFactory', inject(function (questionsFactory) {
        expect(questionsFactory).toBeDefined();
    }));

    describe('questionsFactory', function () {
        var $httpBackend, stateParams, rootScope;
        var service, eightyStoreFactory, scope, modalInstanceMock;

        beforeEach(function() {
            var modalResult = {
                then: function(callback) {
                    callback({question: 'fake question'});
                }
            };

            modalInstanceMock = {
                open: function(options) {
                }
            };

            spyOn(modalInstanceMock, "open")
                .andReturn({ result: modalResult });

            module(function($provide) {
                $provide.value('$modal', modalInstanceMock);
              });

        });

        beforeEach(inject(function (_$httpBackend_, questionsFactory, eightyStore, $rootScope, $stateParams) {
            $httpBackend = _$httpBackend_;
            scope = $rootScope.$new();
            service = questionsFactory;
            eightyStoreFactory = eightyStore;
            stateParams = $stateParams;
            rootScope = $rootScope;
        }));

        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it('checkCollapsed should delete question from set', function () {
            var expand = [
                {id: 1},
                {id: 2},
                {id: 3},
                {id: 4}
            ];
            ;
            var q = {id: 2};
            service.checkCollapsed(expand, q)
            expect(expand.length).toBe(3);
        });

        it('checkCollapsed should add question from set', function () {
            var expand = [
                {id: 1},
                {id: 2},
                {id: 3},
                {id: 4}
            ];
            ;
            var q = {id: 5};
            service.checkCollapsed(expand, q)
            expect(expand.length).toBe(5);
        });

        it('checkInSet should return true if obj contains in $eightyStore', function () {
            var key = '1';
            var q = {id: 1};
            eightyStoreFactory.set('1', [
                {id: 1},
                {id: 2}
            ]);
            expect(service.checkInSet(key, q)).toBe(true);
        });

        it('checkInSet should return false if obj not contains in $eightyStore', function () {
            var key = '1';
            var q = {id: 0};
            eightyStoreFactory.set('1', [
                {id: 1},
                {id: 2}
            ]);
            expect(service.checkInSet(key, q)).toBe(false);
        });

        it('rateUp GET response with error', function () {
            $httpBackend.expectGET('questions/1').respond(404, '');
            spyOn(window, 'errorAlert');
            service.rateUp({id: 1}, scope);
            $httpBackend.flush();
            expect(errorAlert).toHaveBeenCalled();
        });

        it('rateUp PUT response with error', function () {
            var question = { "id": 1, "question": "q1", "answer": "a1", "like": 0, "tags": {}};
            $httpBackend.expectGET('questions/1').respond(question);
            $httpBackend.expectPUT('questions').respond(404, '');
            spyOn(console, 'log');
            service.rateUp(question);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('rateUp set questions like to 1 if there is no likes before', function () {
            var question = { "id": 1, "question": "q1", "answer": "a1", "like": 0, "tags": {}};
            $httpBackend.expectGET('questions/1').respond(question);
            $httpBackend.expectPUT('questions').respond(question);
            service.rateUp(question);
            $httpBackend.flush();
            expect(question.like).toBe(1)
        });

        it('rateUp set questions like to 2 if there is 1 like before', function () {
            var question = { "id": 1, "question": "q1", "answer": "a1", "like": 1, "tags": {}};
            $httpBackend.expectGET('questions/1').respond(question);
            $httpBackend.expectPUT('questions').respond(question);
            service.rateUp(question);
            $httpBackend.flush();
            expect(question.like).toBe(2)
        });

        it('rateUp set questions like to 1 if there is like was null', function () {
            var question = { "id": 1, "question": "q1", "answer": "a1", "like": null, "tags": {}};
            $httpBackend.expectGET('questions/1').respond(question);
            $httpBackend.expectPUT('questions').respond(question);
            service.rateUp(question);
            $httpBackend.flush();
            expect(question.like).toBe(1)
        });

        it('rateUp set questions like to 1 when question is in exportSet', function () {
            var question = { "id": 2, "question": "q2", "answer": "a2", "like": null, "tags": {}};
            $httpBackend.expectGET('questions/2').respond(question);
            $httpBackend.expectPUT('questions').respond(question);
            eightyStoreFactory.set('exportSet', [question]);
            service.rateUp(question);
            $httpBackend.flush();
            expect(question.like).toBe(1);
            expect(eightyStoreFactory.get('exportSet')[0].like).toBe(1);
        });

        it('exportQuestion should add question to the exportSet', function () {
            var question = {id: 3};
            var exportSet = [
                {id: 1},
                {id: 2}
            ];
            eightyStoreFactory.set('exportSet', exportSet);
            service.exportQuestion(question, scope);
            expect(scope.questionsForExport.length).toBe(3);
        });

        it('exportQuestion should delete question from the exportSet', function () {
            var question = {id: 3};
            var exportSet = [
                {id: 1},
                {id: 2},
                {id: 3}
            ];
            eightyStoreFactory.set('exportSet', exportSet);
            service.exportQuestion(question, scope);
            expect(scope.questionsForExport.length).toBe(2);
        });

        it('loadQuestions should load questions', function () {
            stateParams.id = 1;
            var page = 0;
            var questionsSet = [
                {id: 1},
                {id: 2},
                {id: 3}
            ];
            scope.questions = [];
            $httpBackend.expectGET('questions/all/1?page=0&size=100&sort=a.question').respond(questionsSet);
            service.loadQuestions(scope, page);
            $httpBackend.flush();
            expect(scope.questions.length).toBe(3);
        });

        it('loadQuestions with error', function () {
            stateParams.id = 1;
            var page = 0;
            scope.questions = [];
            $httpBackend.expectGET('questions/all/1?page=0&size=100&sort=a.question').respond(404, '');
            spyOn(console, 'log');
            service.loadQuestions(scope, page);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('loadQuestions shouldn\'t load questions if stateParams.id is null', function () {
            stateParams.id = null;
            var page = 1;
            var questionsSet = [
                {id: 1},
                {id: 2},
                {id: 3}
            ];
            scope.questions = [];
            $httpBackend.whenGET('questions/all/null?page=1&size=100&sort=a.question').respond(questionsSet);
            service.loadQuestions(scope, page);
            expect(scope.questions.length).toBe(0);
        });

        it('loadQuestions shouldn\'t create message "No questions" if there are no questions and page nuber is 1', function() {
            stateParams.id = 1;
            var page = 1;
            var questionsSet = [];
            scope.questions = [];
            scope.messsage = '';
            $httpBackend.expectGET('questions/all/1?page=1&size=100&sort=a.question').respond(questionsSet);
            service.loadQuestions(scope, page);
            $httpBackend.flush();
            expect(scope.questions.length).toBe(0);
        });

        it('loadQuestions should create message "No questions" if there are no questions', function() {
            stateParams.id = 1;
            var page = 0;
            var questionsSet = [];
            scope.questions = [];
            scope.messsage = '';
            $httpBackend.expectGET('questions/all/1?page=0&size=100&sort=a.question').respond(questionsSet);
            service.loadQuestions(scope, page);
            $httpBackend.flush();
            expect(scope.message).toBe('No questions');
        });

        it('loadTagsAndCustomersByTopic should load tags and customers', function() {
            scope.tagsAndCustomers = [];
            stateParams.id = 1;
            var tagsSet = [
                {id:1},
                {id:2},
                {id:3}
            ];
            var customersSet = [
                {id:4},
                {id:5},
                {id:6}
            ];
            $httpBackend.expectGET('tags/topic/1').respond(tagsSet);
            $httpBackend.expectGET('customers/topic/1').respond(customersSet);
            service.loadTagsAndCustomersByTopic(scope);
            $httpBackend.flush();
            expect(scope.tagsAndCustomers.length).toBe(6);
        });

        it('loadTagsAndCustomersByTopic response with error', function() {
            $httpBackend.expectGET('tags/topic/1').respond(404, 'tag');
            $httpBackend.expectGET('customers/topic/1').respond(404, 'customer');
            spyOn(console, 'log');
            service.loadTagsAndCustomersByTopic(scope);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('getTopic should return topic', function(){
            stateParams.id = 1;
            var topic = {"id":6, "title":"Persistence", "topics":[{id:1}, {id:2}, {id:3}]};
            $httpBackend.expectGET('topics/1').respond(topic);
            var targetTopic = service.getTopic();
            $httpBackend.flush();
            expect(targetTopic.topics.length).toBe(3);
        });

        it('getTopic with error', function(){
            stateParams.id = 1;
            $httpBackend.expectGET('topics/1').respond(404, '');
            spyOn(console, 'log');
            service.getTopic();
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('after question has succesfully edited saveQuestionChanges should not call clearTags because selectedTags are undefined', function() {
            scope = {
                    clearTags: function () {
                    }
                };
            spyOn(scope, 'clearTags');
            var question = {"question":"q1", "answer":"a1"};
            scope.question = question;
            $httpBackend.expectPUT('questions').respond(question);
            service.saveQuestionChanges(scope, question);
            $httpBackend.flush();
            expect(scope.clearTags).not.toHaveBeenCalled();
        });

        it('after question has succesfully edited saveQuestionChanges should call clearTags methods and question is in export set', function() {
            scope = {
                    selectedTags: [{tag: 'tag 1'}],
                    clearTags: function () {
                    }
                };
            spyOn(scope, 'clearTags');
            var question = {id:1, "question":"q1", "answer":"a1"};
            var questionNew = {id:1, "question":"q2", "answer":"a2"};
            scope.question = questionNew;
            eightyStoreFactory.set('exportSet', [question]);
            scope.questionsForExport = [question];
            $httpBackend.expectPUT('questions').respond(questionNew);
            service.saveQuestionChanges(scope, question);
            $httpBackend.flush();
            expect(scope.clearTags).toHaveBeenCalled();
            expect(scope.questionsForExport[0]).toEqual(questionNew);
            expect(eightyStoreFactory.get('exportSet')[0]).toEqual(questionNew);
        });

        it('saveQuestionChanges shouldn\'t meak request if question is empty', function() {
            scope = {
                    selectedTags: [],
                    clearTags: function () {
                    }
                };
            spyOn(scope, 'clearTags');
            var question = {"question":null, "answer":"a1"};
            scope.question = question;
            service.saveQuestionChanges(scope, question);
        });

        it('saveQuestionChanges with error', function() {
            scope = {
                    selectedTags: [],
                    clearTags: function () {
                    },
                    $close: function (result) {
                    }
                };
            spyOn(scope, 'clearTags');
            spyOn(scope, '$close');
            spyOn(console, 'log');
            var question = {"question":"q1", "answer":"a1"};
            scope.question = question;
            $httpBackend.expectPUT('questions').respond(404, '');
            service.saveQuestionChanges(scope, question);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('clearFilter should clear filtering criteria', function() {
            scope.criteria = 'criteria';
            service.clearFilter(scope);
            expect(scope.criteria).toBe('')
        });

        it('loadCustomersByName sholud return customers by part of name', function() {
            var query = 'cust';
            var customersSet = [
                {id:1},
                {id:2},
                {id:3}
            ];
            var returnedCustomers = [];
            $httpBackend.expectGET('customers/cust').respond(customersSet);
            service.loadCustomersByName(query).then(function(customers) {
                returnedCustomers = customers;
            });
            $httpBackend.flush();
            expect(returnedCustomers.length).toBe(customersSet.length);
        });


        it('loadCustomersByName with error', function() {
            var query = 'cust';
            $httpBackend.expectGET('customers/cust').respond(404, '');
            spyOn(console, 'log');
            service.loadCustomersByName(query);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('loadTagsByName sholud return set of by part of tag name', function() {
            var query = 'ta';
            var tagsSet = [
                {id:1},
                {id:2},
                {id:3}
            ];
            var returnedTags = [];
            $httpBackend.expectGET('tags/ta').respond(tagsSet);
            service.loadTagsByName(query).then(function(tags) {
                returnedTags = tags;
            });
            $httpBackend.flush();
            expect(returnedTags.length).toBe(tagsSet.length);
        });

        it('loadTagsByName with error', function() {
            var query = 'ta';
            $httpBackend.expectGET('tags/ta').respond(404, '');
            spyOn(console, 'log');
            service.loadTagsByName(query);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('add question if there is selected tag', function () {
            var vm  = {
                    selectedTags: [{tag:'tag 1'}],
                    clearTags: function () {
                    },
                    questions: []
                };
            spyOn(vm, 'clearTags');
            spyOn(rootScope, '$broadcast');
            stateParams.id = 11;
            $httpBackend.expectPOST('questions/11').respond({question:'fake question'});
            service.addQuestion(vm);
            $httpBackend.flush();
            expect(vm.clearTags).toHaveBeenCalled();
            expect(vm.questions.length).toBe(1);
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topicTags-update');
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topTags-update');
        });

        it('add question if selected tags are undefined', function () {
            var vm  = {
                    clearTags: function () {
                    },
                    questions: []
                };
            spyOn(vm, 'clearTags');
            spyOn(rootScope, '$broadcast');
            stateParams.id = 11;
            $httpBackend.expectPOST('questions/11').respond({question:'fake question'});
            service.addQuestion(vm);
            $httpBackend.flush();
            expect(vm.clearTags).not.toHaveBeenCalled();
            expect(vm.questions.length).toBe(1);
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topicTags-update');
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topTags-update');
        });

        it('add question with error', function () {
            var vm  = {
                    clearTags: function () {
                    },
                    questions: []
                };
            spyOn(vm, 'clearTags');
            spyOn(rootScope, '$broadcast');
            spyOn(console, 'log');
            stateParams.id = 1;
            $httpBackend.expectPOST('questions/1').respond(404, '');
            service.addQuestion(scope, vm);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('delete question if question is in exportSet and there are selected tags', function () {
            scope = {
                    selectedTags: [{tag:'tag 1'}],
                    clearTags: function () {
                    }
                };
            spyOn(scope, 'clearTags');
            spyOn(rootScope, '$broadcast');
            $httpBackend.expectDELETE('questions/23').respond();
            scope.question = {id:23};
            scope.questions = [{id: 21}, {id:22}, {id:23}];
            eightyStoreFactory.set('exportSet', [scope.question]);
            scope.questionsForExport = [{id:23}];
            service.deleteQuestion(scope, scope.question);
            $httpBackend.flush();
            expect(scope.clearTags).toHaveBeenCalled();
            expect(scope.questions.length).toBe(2);
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topicTags-update');
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topTags-update');
            expect(scope.questionsForExport.length).toBe(0);
            expect(eightyStoreFactory.get('exportSet').length).toBe(0);
        });

        it('delete question if question is not in exportSet and selected tags are undefined', function () {
            scope = {
                    clearTags: function () {
                    }
                };
            spyOn(scope, 'clearTags');
            spyOn(rootScope, '$broadcast');
            $httpBackend.expectDELETE('questions/23').respond();
            scope.question = {id:23};
            scope.questions = [{id: 21}, {id:22}, {id:23}];
            scope.questionsForExport = [];
            service.deleteQuestion(scope, scope.question);
            $httpBackend.flush();
            expect(scope.clearTags).not.toHaveBeenCalled();
            expect(scope.questions.length).toBe(2);
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topicTags-update');
            expect(rootScope.$broadcast).toHaveBeenCalledWith('topTags-update');
            expect(scope.questionsForExport.length).toBe(0);
            expect(eightyStoreFactory.get('exportSet').length).toBe(0);
        });

        it('delete question with error', function () {
            scope = {
                    clearTags: function () {
                    },
                    $close: function (result) {
                    }
                };
            spyOn(scope, 'clearTags');
            spyOn(scope, '$close');
            spyOn(rootScope, '$broadcast');
            spyOn(console, 'log');
            $httpBackend.expectDELETE('questions/20').respond(404, '');
            var question = {id:20};
            service.deleteQuestion(scope, question);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });
    });

});