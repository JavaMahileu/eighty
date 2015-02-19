'use strict';

describe("addQuestionInstanceCtrl", function () {

    var scope, ctrl, modalD, service;

    beforeEach(module('eightyFactories'));
    beforeEach(module('eightyControllers'));

    beforeEach(inject(function (modalData, questionsFactory, $rootScope, $controller) {
        scope = $rootScope.$new();
        service = questionsFactory;
        service = {
                  loadCustomersByName: function(query) {
                  },
                  loadTagsByName: function(query) {
                  }
            };

        scope = {
                $close: function (result) {
                },
                $dismiss: function () {
                }
            };
        ctrl = $controller("addQuestionInstanceCtrl", {$scope: scope, questionsFactory: service});
        spyOn(scope, "$close");
        spyOn(scope, "$dismiss");
        spyOn(service, "loadCustomersByName").andReturn('customer 1');
        spyOn(service, "loadTagsByName").andReturn('tag 1');
        modalD = modalData;
    }));

    it("create question click", function () {
        ctrl.question = {question: 'fake question'};
        ctrl.createQuestion();
        expect(modalD.getShouldBeOpen()).toBeFalsy();
        expect(ctrl.question.like).toBe(0);
        expect(scope.$close).toHaveBeenCalledWith(ctrl.question);
    });

    it("create question click when question is null", function () {
        ctrl.question = {};
        ctrl.createQuestion();
        expect(modalD.getShouldBeOpen()).toBeFalsy();
        expect(scope.$close).not.toHaveBeenCalled();
    });

    it("cancel add question click", function () {
        ctrl.cancelAddQuestion();
        expect(modalD.getShouldBeOpen()).toBeFalsy();
        expect(scope.$dismiss).toHaveBeenCalled();
    });

    it("load customers", function () {
        expect(ctrl.loadCustomers('customer1')).toBe('customer 1');
    });

    it("load tags", function () {
        expect(ctrl.loadTags('tag1')).toBe('tag 1');
    });
});

