'use strict';

describe('service', function () {

    beforeEach(module('eightyFactories'));

    it('check the existence of tagsFactory', inject(function (tagsFactory) {
        expect(tagsFactory).toBeDefined();
    }));

    describe('tagsFactory', function () {
        var $httpBackend, service;

        beforeEach(inject(function (_$httpBackend_, tagsFactory) {
            $httpBackend = _$httpBackend_;
            service = tagsFactory;
        }));

        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it('getTopNTags', function () {
            var tagsSet = [
                {id: 1},
                {id: 2}
            ];
            var tagsResult;
            $httpBackend.expectGET('tags/top/10').respond(tagsSet);
            service.getTopNTags().then(function (tags) {
                tagsResult = tags;
            });
            $httpBackend.flush();
            expect(tagsSet[0].id).toEqual(tagsResult[0].id);
            expect(tagsSet[1].id).toEqual(tagsResult[1].id);
            expect(tagsResult.length).toEqual(2);
        });

        it('getTopNTags', function () {
            var tagsSet = [
                {id: 1},
                {id: 2}
            ];
            var tagsResult;
            $httpBackend.expectGET('tags/top/10').respond(tagsSet);
            service.getTopNTags().then(function (tags) {
                tagsResult = tags;
            });
            $httpBackend.flush();
            expect(tagsSet[0].id).toEqual(tagsResult[0].id);
            expect(tagsSet[1].id).toEqual(tagsResult[1].id);
            expect(tagsResult.length).toEqual(2);
        });

        it('getTopNTags with error', function () {
            spyOn(console, 'log');
            spyOn(window, 'errorAlert');
            $httpBackend.expectGET('tags/top/10').respond(404, '');
            service.getTopNTags();
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });
    });

});