'use strict';

describe('service', function () {

    beforeEach(module('eightyFactories'));

    it('check the existence of customersFactory', inject(function (customersFactory) {
        expect(customersFactory).toBeDefined();
    }));

    describe('customersFactory', function () {
        var $httpBackend, service;

        beforeEach(inject(function (_$httpBackend_, customersFactory) {
            $httpBackend = _$httpBackend_;
            service = customersFactory;
        }));

        afterEach(function() {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it('getCustomers', function () {
            var customersSet = [
                {id: 1},
                {id: 2}
            ];
            var customersResult;
            $httpBackend.expectGET('customers').respond(customersSet);
            service.getCustomers().then(function (customers) {
                customersResult = customers;
            });
            $httpBackend.flush();
            expect(customersSet[0].id).toEqual(customersResult[0].id);
            expect(customersSet[1].id).toEqual(customersResult[1].id);
            expect(customersResult.length).toEqual(2);
        });

        it('getCustomers with error', function () {
            spyOn(console, 'log');
            spyOn(window, 'errorAlert');
            $httpBackend.expectGET('customers').respond(404, '');
            service.getCustomers();
            $httpBackend.flush();
            expect(console.log).toHaveBeenCalled();
        });
    });

});