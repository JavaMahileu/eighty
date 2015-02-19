'use strict';

angular.module('eightyApp.test', ['eightyApp', 'ngMockE2E']).
    run(function($httpBackend) {
        $httpBackend.whenPOST('questions/1').respond(function(method, url, data) {
            return [200, data, {}];
        });
        $httpBackend.whenPOST('topics/0').respond(function(method, url, data) {
            return [200, data, {}];
        });
        $httpBackend.whenPOST('topics/1').respond(function(method, url, data) {
            return [200, data, {}];
        });
        $httpBackend.whenGET('customers').respond([{id:51,name:'customer1',count:2}, {id:52,name:'customer2',count:2}]);
        $httpBackend.whenGET(/^\w+.*/).passThrough();
        $httpBackend.whenPUT(/^\w+.*/).passThrough();
        $httpBackend.whenDELETE(/^\w+.*/).passThrough();
    });
