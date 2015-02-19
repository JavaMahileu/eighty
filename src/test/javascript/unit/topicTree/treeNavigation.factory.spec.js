'use strict';

describe('service', function () {

    beforeEach(module('eightyApp'));

    it('check the existence of modalData', inject(function (treeNavigationFactory) {
        expect(treeNavigationFactory).toBeDefined();
    }));

    beforeEach(function () {
        this.addMatchers({
            toEqualData: function (expected) {
                return angular.equals(this.actual, expected);
            }
        });
    });

    describe('treeNavigationFactory', function () {
        var scope, $httpBackend, modalInstanceMock, service;

        var rootTree = [{"title": "Programming Languages", "data": {"id": 1}, "topics": [{"title": "Java", "data": {"id": 6}}]},{"title": "Persistence", "data": {"id": 2}, "topics": [{"title": "Query Lanquages", "data": {"id": 3}}]}];
        var topic6 = {"id": 6, "title": "Java", "topics": [{"id": 7, "title": null, "topics": [], "questions": []}]};

        beforeEach(function() {
            var modalResult = {
                then: function(callback) {
                    callback(topic6);
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

        beforeEach(inject(function (_$httpBackend_, $rootScope, treeNavigationFactory) {
            $httpBackend = _$httpBackend_;

            scope = $rootScope.$new();
            scope.treedata = rootTree;
            service = treeNavigationFactory;

            scope.treeControl = {
                expand_branch: function () {
                },
                get_parent_branch: function (node) {
                  return scope.treedata[0];
                },
                select_parent_branch: function (node) {
                },
            };
        }));

        it('add topic', function () {
            expect(scope.treedata[1].topics.length).toEqualData(1);
            $httpBackend.expectPOST('topics/2').respond(topic6);
            service.addTopic(scope, scope.treedata[1]);
            $httpBackend.flush();
            expect(scope.treedata[1].topics.length).toEqualData(2);
        });

        it('add topic with error', function () {
            $httpBackend.expectPOST('topics/2').respond(404, '');
            spyOn(console, 'log');
            spyOn(window, 'errorAlert');
            service.addTopic(scope, scope.treedata[1]);
            $httpBackend.flush();
            expect(errorAlert).toHaveBeenCalled();
            expect(console.log).toHaveBeenCalled();
        });

        it('add topic in a root', function () {
            expect(scope.treedata.length).toEqualData(2);
            $httpBackend.expectPOST('topics/0').respond(topic6);
            service.addTopic(scope);
            $httpBackend.flush();
            expect(scope.treedata.length).toEqualData(3);
        });

        it('edit topic', function () {
            $httpBackend.expectGET('topics/6').respond(topic6);
            $httpBackend.expectPUT('topics').respond(topic6);
            service.editTopic(scope.treedata[0].topics[0]);
            $httpBackend.flush();
            expect(scope.treedata[0].topics[0].title).toEqualData(topic6.title);
        });

        it('edit topic with error while getting topic', function () {
            $httpBackend.expectGET('topics/6').respond(404, '');
            spyOn(console, 'log');
            service.editTopic(scope.treedata[0].topics[0]);
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });

        it('edit topic with error while updating topic', function () {
            $httpBackend.expectGET('topics/6').respond(topic6);
            $httpBackend.expectPUT('topics').respond(404, '');
            spyOn(console, 'log');
            spyOn(window, 'errorAlert');
            service.editTopic(scope.treedata[0].topics[0]);
            $httpBackend.flush();
            expect(errorAlert).toHaveBeenCalled();
            expect(console.log).toHaveBeenCalled();
        });

        it('delete topic with error', function () {
            $httpBackend.expectDELETE('topics/6?title=Java&topics=%7B%22id%22:7,%22title%22:null,%22topics%22:%5B%5D,%22questions%22:%5B%5D%7D').respond(404, '');
            spyOn(console, 'log');
            spyOn(window, 'errorAlert');
            service.deleteTopic(scope.treedata[0].topics[0], scope);
            $httpBackend.flush();
            expect(errorAlert).toHaveBeenCalled();
            expect(console.log).toHaveBeenCalled();
        });

        it('delete topic', function () {
            $httpBackend.expectDELETE('topics/6?title=Java&topics=%7B%22id%22:7,%22title%22:null,%22topics%22:%5B%5D,%22questions%22:%5B%5D%7D').respond();
            expect(scope.treedata[0].topics.length).toEqualData(1);
            service.deleteTopic(scope.treedata[0].topics[0], scope);
            $httpBackend.flush();
            expect(scope.treedata[0].topics.length).toEqualData(0);
        });
    });

});