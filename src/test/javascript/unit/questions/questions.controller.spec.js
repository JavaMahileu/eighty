'use strict';

describe('topicQuestionsCtrl', function() {

    var scope, ctrl, $httpBackend, factory, stateParams;

    var qRespond = {id: 20, question: 'fakeQuestion', answer: '', tags: [], customers: []};
    var qRequest = {question: 'fakeQuestion', answer: '', tags: [], customers: []};

    beforeEach(module('eightyFactories'));
    beforeEach(module('eightyControllers'));

    beforeEach(inject(function (_$httpBackend_, $rootScope, $controller, modalData, questionsFactory, $stateParams) {
        $httpBackend = _$httpBackend_;
        scope = $rootScope.$new();
        factory = questionsFactory;
        stateParams = $stateParams;
        ctrl = $controller('topicQuestionsCtrl', {$scope: scope, modalData: modalData, questionsFactory: questionsFactory});
        $httpBackend.whenGET('topics/1').respond(200, '');
        $httpBackend.whenGET('questions/all/1?page=0&size=100&sort=a.question').respond(200, '');
        $httpBackend.whenGET('tags/topic/1').respond(200, '');
        $httpBackend.whenGET('customers/topic/1').respond(200, '');
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('loadMore should load more questions', function() {
        $httpBackend.expectGET('topics').respond(200, '');
        $httpBackend.expectGET('tags/topic').respond(200, '');
        $httpBackend.expectGET('customers/topic').respond(200, '');
        $httpBackend.expectGET('questions/all/1?page=1&size=100&sort=a.question').respond(200, '');
        $httpBackend.expectGET('questions/all/1?page=1&size=100&sort=a.question').respond(200, '');
        stateParams.id = 1;
        factory.loadQuestions(ctrl, 1);
        ctrl.loadMore();
        $httpBackend.flush();
    });

    it('clearFilter should clear filter', function() {
        ctrl.criteria = 'criteria';
        ctrl.clearFilter();
        expect(ctrl.criteria).toBe('');
        $httpBackend.flush();
    });

    it('addQuestion should load html template addquestion.html', function() {
        ctrl.addQuestion();
        $httpBackend.whenGET('pages/addquestion.html').respond(200, '');
        $httpBackend.flush();
    });

    it('editQuestion', function() {
        $httpBackend.expectGET('pages/editquestion.html').respond(200, '');
        ctrl.editQuestion(qRequest);
        $httpBackend.flush();
    });

    it('rateUp should call factory.rateUp', function() {
        $httpBackend.expectGET('questions/20').respond(200, qRespond);
        $httpBackend.expectPUT('questions').respond(200, '');
        ctrl.rateUp(qRespond);
        $httpBackend.flush();
    });

    it('exportQuestion', function() {
        ctrl.exportQuestion(qRespond);
        expect(factory.getExport().length).toBe(1);
        $httpBackend.flush();
    });

    it('isCollapsedAnswer return true if checkCollapsed runnig first time with given question', function() {
        ctrl.checkCollapsed(qRespond);
        expect(ctrl.isCollapsedAnswer(qRespond)).toBe(true);
        $httpBackend.flush();
    });

    it('onTagClick should add tag to selectedTags', function() {
        ctrl.selectedTags = [];
        var tag = {"id":31,"tag":"tag1","count":2,"countInTopic":2};
        ctrl.onTagClick(tag);
        expect(ctrl.selectedTags.length).toBe(1);
        $httpBackend.flush();
    });

    it('onTagClick should delete tag from selectedTags', function() {
        ctrl.selectedTags = [];
        var tag = {"id":31,"tag":"tag1","count":2,"countInTopic":2};
        ctrl.selectedTags.push(tag);
        ctrl.onTagClick(tag);
        expect(ctrl.selectedTags.length).toBe(0);
        $httpBackend.flush();
    });

    it('onTagClick should add tag to selectedTags', function() {
        var tag = {"id":31,"tag":"tag1","count":2,"countInTopic":2};
        ctrl.onTagClick(tag);
        expect(angular.isUndefined(ctrl.selectedTags)).toBe(false);
        $httpBackend.flush();
    });

    it('isTagSelected should return true if tag was selected', function() {
        var tag = {"id":31,"tag":"tag1","count":2,"countInTopic":2};
        ctrl.selectedTags = [];
        ctrl.selectedTags.push(tag);
        expect(ctrl.isTagSelected(tag)).toBe(true);
        $httpBackend.flush();
    });

    it('isTagSelected should return false if tag wasn\'t selected', function() {
        var tag = {"id":31,"tag":"tag1","count":2,"countInTopic":2};
        ctrl.selectedTags = [];
        expect(ctrl.isTagSelected(tag)).toBe(false);
        $httpBackend.flush();
    });

    it('isTagSelected should return false if selectedTags is undefined', function() {
        var tag = {"id":31,"tag":"tag1","count":2,"countInTopic":2};
        expect(ctrl.isTagSelected(tag)).toBe(false);
        $httpBackend.flush();
    });

    it('clearTags should delete all tags from selectedTags', function() {
        ctrl.selectedTags = [];
        var tag = {"id":31,"tag":"tag1","count":2,"countInTopic":2};
        ctrl.selectedTags.push(tag);
        ctrl.clearTags();
        expect(ctrl.selectedTags.length).toBe(0);
        $httpBackend.flush();
    });
});
