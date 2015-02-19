'use strict';

describe('treeNavigationCtrl', function () {
    var ctrl, $httpBackend, modalControllerMock, stateMock, treeNavigationFactoryMock;

    var rootTopic = {"id": 0, "title": "root", "topics": [{"id": 1, "title": "Programming Languages", "topics": [{"id": 6, "title": "Java", "topics": [{"id": 7, "title": null, "topics": [], "questions": []}], "questions": []}], "questions": []},{"id": 2, "title": "Persistence", "topics": [{"id": 3, "title": "Query Lanquages", "topics": [{"id": 4, "title": null, "topics": [], "questions": []},{"id": 5, "title": null, "topics": [], "questions": []}], "questions": []}], "questions": []}], "questions": []};
    var topic1 = {"id": 1, "title": "Programming Languages", "topics": [{"id": 6, "title": "Java", "topics": [{"id": 7, "title": null, "topics": [], "questions": []}], "questions": []}], "questions": []};
    var rootTree = [{"title": "Programming Languages", "data": {"id": 1}, "topics": [{"title": "Java", "data": {"id": 6}}]},{"title": "Persistence", "data": {"id": 2}, "topics": [{"title": "Query Lanquages", "data": {"id": 3}}]}];
    var tree1 = [{"title": "Programming Languages", "data": {"id": 1}, "topics": [{"title": "Java", "data": {"id": 6}, "topics": [{"title": null, "data": {"id": 7}}]}]},{"title": "Persistence", "data": {"id": 2}, "topics": [{"title": "Query Lanquages", "data": {"id": 3}}]}];

    beforeEach(module('eightyFactories'));
    beforeEach(module('eightyControllers'));

    beforeEach(function () {
        this.addMatchers({
            toEqualData: function (expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    beforeEach(inject(function (_$httpBackend_, $rootScope, $controller, treeNavigationFactory) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expectGET('topics').respond(rootTopic);

        stateMock = {
                go: function(topics, node) {
                }
            };
        spyOn(stateMock, "go");

        treeNavigationFactoryMock = treeNavigationFactory;

        ctrl = $controller("treeNavigationCtrl", {
            $state: stateMock
        });

        ctrl.treeControl = {
            expand_branch: function () {
            },
        };
    }));

    it('should create root "treedata" with 2 topics fetched from xhr', function () {
        expect(ctrl.treedata).toEqualData([]);
        $httpBackend.flush();
        expect(ctrl.treedata).toEqualData(rootTree);
    });

    it('should add branch to root "treedata"', function () {
        expect(ctrl.treedata).toEqualData([]);
        $httpBackend.flush();
        expect(ctrl.treedata).toEqualData(rootTree);
        ctrl.onFolderHeadClick(ctrl.treedata[0]);
        $httpBackend.expectGET('topics/1').respond(topic1);
        $httpBackend.flush();
        expect(ctrl.treedata[0].topics).toEqualData(tree1[0].topics);
    });

    it('onFolderHeadClick with error', function () {
        expect(ctrl.treedata).toEqualData([]);
        $httpBackend.flush();
        $httpBackend.expectGET('topics/1').respond(404, '');
        spyOn(console, 'log');
        ctrl.onFolderHeadClick(ctrl.treedata[0]);
        $httpBackend.flush();
        expect(console.log).toHaveBeenCalled();
    });

    it('click on topic', function () {
        $httpBackend.flush();
        ctrl.onTopicClick(ctrl.treedata[0]);
        expect(stateMock.go).toHaveBeenCalledWith('topics', {id:1})
    });

    it('add topic', function () {
        $httpBackend.flush();
        treeNavigationFactoryMock.addTopic = function(vm, node) {
        };
        spyOn(treeNavigationFactoryMock, "addTopic");
        ctrl.addTopic(ctrl.treedata[0]);
        expect(treeNavigationFactoryMock.addTopic).toHaveBeenCalledWith(ctrl, ctrl.treedata[0]);
    });

    it('edit topic', function () {
        $httpBackend.flush();
        treeNavigationFactoryMock.editTopic = function(node) {
        };
        spyOn(treeNavigationFactoryMock, "editTopic");
        ctrl.editTopic(ctrl.treedata[0]);
        expect(treeNavigationFactoryMock.editTopic).toHaveBeenCalledWith(ctrl.treedata[0]);
    });

    it('delete topic', function () {
        $httpBackend.flush();
        treeNavigationFactoryMock.deleteTopic = function(vm, node) {
        };
        spyOn(treeNavigationFactoryMock, "deleteTopic");
        ctrl.deleteTopic(ctrl.treedata[0]);
        expect(treeNavigationFactoryMock.deleteTopic).toHaveBeenCalledWith(ctrl.treedata[0], ctrl);
    });

    it('get/set editable', function () {
        $httpBackend.flush();
        expect(ctrl.getEditable()).toEqualData(false);
        ctrl.setEditable(true);
        expect(ctrl.getEditable()).toEqualData(true);
    });
});
